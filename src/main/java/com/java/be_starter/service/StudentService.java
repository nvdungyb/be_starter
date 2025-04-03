package com.java.be_starter.service;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(StudentCreationDto dto);

    Student updateStudent(long studentId, StudentUpdateDto dto);

    List<Student> findStudentByPage(int pageNo);
}
