package com.java.be_starter.repository.dao;

import com.java.be_starter.entity.Student;
import com.java.be_starter.service.StudentService;

public interface StudentDao extends StudentService {
    boolean existsById(long id);

    Student saveStudent(Student student);

    Student findById(long id);

}
