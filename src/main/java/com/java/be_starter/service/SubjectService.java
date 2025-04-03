package com.java.be_starter.service;

import com.java.be_starter.dto.request.SubjectCreationDto;
import com.java.be_starter.dto.request.SubjectUpdateDto;
import com.java.be_starter.entity.Subject;

import java.util.List;

public interface SubjectService {
    Subject createSubject(SubjectCreationDto dto);

    Subject updateSubject(long id, SubjectUpdateDto dto);

    List<Subject> findByPage(int pageNo);

    void deleteSubject(long id);
}
