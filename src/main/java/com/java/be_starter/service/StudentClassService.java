package com.java.be_starter.service;

import com.java.be_starter.dto.request.StudentClassCreationDto;
import com.java.be_starter.entity.StudentClass;

import java.util.List;

public interface StudentClassService {
    StudentClass createStudentClass(StudentClassCreationDto dto);

    void deleteStudentClass(long id);

    List<StudentClass> getStudentClassByPage(int pageNo);
}
