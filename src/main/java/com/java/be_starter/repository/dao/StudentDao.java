package com.java.be_starter.repository.dao;

import com.java.be_starter.dto.CreationDto;
import com.java.be_starter.dto.UpdateDto;
import com.java.be_starter.service.StudentService;

public interface StudentDao<T, V extends CreationDto, K extends UpdateDto, U> extends StudentService<T, V, K, U> {
    boolean existsById(U id);

    T saveStudent(T student);

    T findById(U id);
}

