package com.java.be_starter.service;

import com.java.be_starter.dto.request.TeacherCreationDto;
import com.java.be_starter.dto.request.TeacherUpdateDto;
import com.java.be_starter.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher createTeacher(TeacherCreationDto dto);

    Teacher updateTeacher(long id, TeacherUpdateDto dto);

    List<Teacher> findTeacherByPage(int pageNo);

    Teacher findTeacherById(long id);
}
