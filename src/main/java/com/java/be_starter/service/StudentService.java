package com.java.be_starter.service;

import com.java.be_starter.dto.CreationDto;
import com.java.be_starter.dto.UpdateDto;

import java.util.List;

public interface StudentService<U, V extends CreationDto, K extends UpdateDto> {
    U createStudent(V studentCreationDto);

    U updateStudent(long studentId, K studentUpdateDto);

    List<U> findStudentByPage(int pageNo);
}
