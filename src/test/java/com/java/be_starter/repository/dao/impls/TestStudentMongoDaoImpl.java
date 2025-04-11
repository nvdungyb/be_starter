package com.java.be_starter.repository.dao.impls;

import com.java.be_starter.dto.request.documents.StudentCreationDocumentDto;
import com.java.be_starter.dto.request.documents.StudentUpdateDocumentDto;
import com.java.be_starter.entity.documents.StudentMongo;
import com.java.be_starter.repository.dao.impls.mongo_template.StudentMongoDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestStudentMongoDaoImpl {
    @Autowired
    private StudentMongoDaoImpl dao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testCreateStudent() {
        StudentCreationDocumentDto dto = StudentCreationDocumentDto.builder()
                .name("Test")
                .phone("1231231231")
                .email("test@test.com")
                .major("IT")
                .city("HN")
                .state("HADONG")
                .street("km10, Nguyen Trai")
                .dob(new Date())
                .year(2)
                .build();

        StudentMongo savedStudent = dao.createStudent(dto);
        System.out.println(savedStudent);
        assert savedStudent != null;
    }

    @Test
    public void testExecuteUpdateStudent() {
        String studentId = "67f8dbbe24137853ad1ed851";
        String updateName = "Nguyen Trai";
        StudentUpdateDocumentDto dto = StudentUpdateDocumentDto.builder()
                .name(updateName)
                .phone("1234567890")
                .build();

        StudentMongo updateStudent = dao.updateStudent(studentId, dto);
        System.out.println(updateStudent);
        assert updateStudent.getName().equals(updateName);
    }

    @Test
    public void testExistById() {
        String id = "67f8dbbe24137853ad1ed851";
        assert dao.existsById(id) == true;
    }

    @Test
    public void testFindById() {
        String id = "67f8dbbe24137853ad1ed851";
        assert dao.findById(id).getEmail().equals("nguyenvana33example.com");
    }

    @Test
    public void testPaginationStudents() {
        int pageNo = 1;
        List<StudentMongo> studentList = dao.findStudentByPage(pageNo);
        System.out.println(studentList);
        assert dao.findStudentByPage(pageNo).size() == 3;
    }

    @Test
    public void testDeleteStudentById() {
        String id = "67f8db4724137853ad1ed850";
        dao.deleteById(id);
        assert !dao.existsById(id);
    }

    @AfterEach
    public void tearDown() {
        mongoTemplate.remove(new Query(Criteria.where("email").is("test@test.com")), StudentMongo.class);
    }
}
