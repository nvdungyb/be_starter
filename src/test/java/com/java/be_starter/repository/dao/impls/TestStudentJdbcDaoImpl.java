package com.java.be_starter.repository.dao.impls;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import com.java.be_starter.repository.dao.impls.jdbc_template.StudentJdbcDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Rollback
public class TestStudentJdbcDaoImpl {
    @Autowired
    private StudentJdbcDaoImpl studentJdbcDaoImpl;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCheckExistsById() {
        long id = 16;
        assert studentJdbcDaoImpl.existsById(id) == true;
    }

    @Test
    @Transactional
    public void testSaveStudent() {
        Student student = Student.builder()
                .person(Person.builder()
                        .name("admin")
                        .email("admin22@gmail.com")
                        .dob(new Date())
                        .address("Mo lao")
                        .phone("012326812")
                        .build())
                .major("IT")
                .year(4)
                .build();

        Student savedStudent = studentJdbcDaoImpl.saveStudent(student);
        System.out.println(savedStudent);

        assert savedStudent != null;
    }

    @Test
    public void testFindById() {
        Long id = 17l;
        Student student = studentJdbcDaoImpl.findById(id);
        System.out.println(student);
        assert student != null;
    }

    @Test
//    @Transactional
    public void testCreateStudent() {
        StudentCreationDto dto = StudentCreationDto.builder()
                .address("Mo lao")
                .dob(new Date())
                .email("admin23@gmail.com")
                .name("admin")
                .phone("013976789")
                .major("IT")
                .year(3)
                .build();

        Student savedStudent = studentJdbcDaoImpl.createStudent(dto);
        System.out.println(savedStudent);
        assert savedStudent != null;
    }

    @Test
    @Transactional
    public void testUpdateStudent() {
        StudentUpdateDto dto = StudentUpdateDto.builder()
                .name("admin")
                .phone("0123556712")
                .address("Mo lao")
                .dob(new Date())
                .major("IT")
                .year(4)
                .build();

        Long studentId = 16L;

        Student updatedStudent = studentJdbcDaoImpl.updateStudent(studentId, dto);
        System.out.println(updatedStudent);
        assert updatedStudent != null;
    }

    @Test
    public void testGetStudentByPage() {
        int pageNo = 101;
        List<Student> studentList = studentJdbcDaoImpl.findStudentByPage(pageNo);
        System.out.println(studentList.size());
        assert studentList.size() != 0;
    }

    @Test
    public void testDeleteById() {
        Long studentId = 1036l;
        studentJdbcDaoImpl.deleteById(studentId);
        assert studentJdbcDaoImpl.existsById(studentId) == false;
    }

    @AfterEach
    public void tearDown() {
        String studentEmail = "admin23@gmail.com";
        String deleteStudentSql = "DELETE s FROM student s " +
                "INNER JOIN person p ON s.person_id = p.id " +
                "WHERE p.email = ?";

        String deletePersonSql = "DELETE s FROM person s WHERE s.email = ?";
        jdbcTemplate.update(deleteStudentSql, studentEmail);
        jdbcTemplate.update(deletePersonSql, studentEmail);
    }
}
