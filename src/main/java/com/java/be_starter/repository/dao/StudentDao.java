package com.java.be_starter.repository.dao;

import com.java.be_starter.dto.CreationDto;
import com.java.be_starter.dto.UpdateDto;
import com.java.be_starter.service.StudentService;

public interface StudentDao<T, V extends CreationDto, K extends UpdateDto> extends StudentService<T, V, K> {
    boolean existsById(long id);

    T saveStudent(T student);

    T findById(long id);
}

