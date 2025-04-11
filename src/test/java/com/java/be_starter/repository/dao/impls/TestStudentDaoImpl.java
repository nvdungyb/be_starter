package com.java.be_starter.repository.dao.impls;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import com.java.be_starter.repository.dao.impls.jdbc_template.StudentDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Rollback
public class TestStudentDaoImpl {
    @Autowired
    private StudentDaoImpl studentDaoImpl;

    @Test
    public void testCheckExistsById() {
        long id = 16;
        assert studentDaoImpl.existsById(id) == true;
    }

    @Test
    @Transactional
    public void testSaveStudent() {
        Student student = Student.builder()
                .person(Person.builder()
                        .name("admin")
                        .email("admin@gmail.com")
                        .dob(new Date())
                        .address("Mo lao")
                        .phone("0123456712")
                        .build())
                .major("IT")
                .year(4)
                .build();

        Student savedStudent = studentDaoImpl.saveStudent(student);
        System.out.println(savedStudent);

        assert savedStudent != null;
    }

    @Test
    public void testFindById() {
        int id = 16;
        Student student = studentDaoImpl.findById(id);
        System.out.println(student);
        assert student != null;
    }

    @Test
    @Transactional
    public void testCreateStudent() {
        StudentCreationDto dto = StudentCreationDto.builder()
                .address("Mo lao")
                .dob(new Date())
                .email("admin@gmail.com")
                .name("admin")
                .phone("0123456712")
                .major("IT")
                .year(3)
                .build();

        Student savedStudent = studentDaoImpl.createStudent(dto);
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

        int studentId = 16;

        Student updatedStudent = studentDaoImpl.updateStudent(studentId, dto);
        System.out.println(updatedStudent);
        assert updatedStudent != null;
    }

    @Test
    public void testGetStudentByPage() {
        int pageNo = 101;
        List<Student> studentList = studentDaoImpl.findStudentByPage(pageNo);
        System.out.println(studentList.size());
        assert studentList.size() != 0;
    }
}
