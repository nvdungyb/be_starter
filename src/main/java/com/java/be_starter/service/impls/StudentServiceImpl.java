package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import com.java.be_starter.exceptions.EntityConfigException;
import com.java.be_starter.exceptions.InvalidEntityException;
import com.java.be_starter.repository.StudentRepository;
import com.java.be_starter.service.StudentService;
import com.java.be_starter.utils.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService<Student, StudentCreationDto, StudentUpdateDto> {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final static int ELEMENTS_PER_PAGE = 10;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional
    public Student createStudent(StudentCreationDto studentCreationDto) {
        Student student = studentMapper.toEntity(studentCreationDto);

        Student savedStudent;
        try {
            savedStudent = saveStudent(student);
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException(e);
        } catch (OptimisticLockingFailureException e) {
            throw new EntityConfigException(e);
        }
        return savedStudent;
    }

    private Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(long studentId, StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(studentId)));

        student.setYear(studentUpdateDto.getYear() != null ? studentUpdateDto.getYear() : student.getYear());
        student.setMajor(studentUpdateDto.getMajor() != null ? studentUpdateDto.getMajor() : student.getMajor());
        Person person = student.getPerson();
        person.setName(studentUpdateDto.getName() == null ? person.getName() : studentUpdateDto.getName());
        person.setPhone(studentUpdateDto.getPhone() == null ? person.getPhone() : studentUpdateDto.getPhone());
        person.setAddress(studentUpdateDto.getAddress() == null ? person.getAddress() : studentUpdateDto.getAddress());
        person.setDob(studentUpdateDto.getDob() == null ? person.getDob() : studentUpdateDto.getDob());
        student.setPerson(person);

        return saveStudent(student);
    }

    public List<Student> findAllStudent() {
        return (List<Student>) studentRepository.findAll();
    }

    public List<Student> findStudentByPage(int pageId) {
        // we can pass sort detail in Pageable
//        Pageable pageableWithSort = PageRequest.of(pageId - 1, ELEMENTS_PER_PAGE, Sort.by("major"));
        Pageable pageable = PageRequest.of(pageId - 1, ELEMENTS_PER_PAGE);
        Page<Student> page = studentRepository.findAll(pageable);

        return page.getContent();
    }
}
