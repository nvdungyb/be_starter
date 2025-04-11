package com.java.be_starter.repository.dao.impls.jdbc_template;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import com.java.be_starter.exceptions.SaveEntityException;
import com.java.be_starter.repository.dao.StudentDao;
import com.java.be_starter.utils.jdbc.mapper.StudentRowMapper;
import com.java.be_starter.utils.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentJdbcDaoImpl implements StudentDao {
    private final JdbcTemplate jdbcTemplate;
    private final PersonJdbcDaoImpl personJdbcDaoImpl;
    private final StudentRowMapper studentRowMapper;
    private final StudentMapper studentMapper;
    private final int ELEMENTS_PER_PAGE = 10;

    public StudentJdbcDaoImpl(JdbcTemplate jdbcTemplate, PersonJdbcDaoImpl personJdbcDaoImpl, StudentRowMapper studentRowMapper, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.personJdbcDaoImpl = personJdbcDaoImpl;
        this.studentRowMapper = studentRowMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public boolean existsById(long id) {
        String sql = "SELECT COUNT(*) FROM student WHERE student_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        Person person = student.getPerson();
        Person savedPerson = personJdbcDaoImpl.savePerson(person);
        student.setPerson(savedPerson);

        try {
            String sql = "INSERT INTO student (student_id, major, year, person_id) VALUES (?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setLong(1, student.getStudentId());
                ps.setString(2, student.getMajor());
                ps.setInt(3, student.getYear());
                ps.setLong(4, student.getPerson().getId());
                return ps;
            }, keyHolder);

            student.setStudentId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return student;
        } catch (DataAccessException | NullPointerException e) {
            throw new SaveEntityException(e.getMessage());
        }
    }

    @Override
    public Student findById(long studentId) {
        String sql = "SELECT s.student_id, s.major, s.year, s.person_id, p.email, p.name, p.dob, p.phone, p.address " +
                "FROM student s INNER JOIN person p ON s.person_id = p.id " +
                "WHERE s.student_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, studentRowMapper, studentId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Transactional
    @Override
    public Student createStudent(StudentCreationDto dto) {
        Student student = studentMapper.toEntity(dto);
        return saveStudent(student);
    }

    @Transactional
    @Override
    public Student updateStudent(long studentId, StudentUpdateDto dto) {
        // Đã handler trường hợp không có row nào trả về.
        Student student = findById(studentId);

        Person person = student.getPerson();
        person.setAddress(dto.getAddress() == null ? person.getAddress() : dto.getAddress());
        person.setDob(dto.getDob() == null ? person.getDob() : dto.getDob());
        person.setName(dto.getName() == null ? person.getName() : dto.getName());
        person.setPhone(dto.getPhone() == null ? person.getPhone() : dto.getPhone());
        student.setPerson(person);
        student.setMajor(dto.getMajor() == null ? student.getMajor() : dto.getMajor());
        student.setYear(dto.getYear() == null ? student.getYear() : dto.getYear());

        Person updatedPerson = personJdbcDaoImpl.executeUpdate(person);
        Student updatedStudent = executeUpdate(student);

        return updatedStudent;
    }

    @Transactional
    protected Student executeUpdate(Student student) {
        try {
            String sql = "UPDATE student SET year = ?, major = ? WHERE student_id = ?";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, student.getYear());
                ps.setString(2, student.getMajor());
                ps.setLong(3, student.getStudentId());
                return ps;
            });
            return student;
        } catch (DataAccessException e) {
            throw new SaveEntityException(e.getMessage());
        }
    }

    @Override
    public List<Student> findStudentByPage(int pageNo) {
        try {
            int offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
            String sql = "SELECT s.student_id, s.major, s.year, s.person_id, " +
                    "p.email, p.name, p.dob, p.phone, p.address " +
                    "FROM student s " +
                    "INNER JOIN person p ON s.person_id = p.id " +
                    "ORDER BY s.student_id " +
                    "LIMIT ? OFFSET ?";

            return jdbcTemplate.query(sql, studentRowMapper, ELEMENTS_PER_PAGE, offset);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(e);
        }
    }
}

