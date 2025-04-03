package com.java.be_starter.utils.mapper;

import com.java.be_starter.dto.request.TeacherCreationDto;
import com.java.be_starter.dto.response.TeacherResponseDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public Teacher toEntity(TeacherCreationDto teacherCreationDto) {
        return Teacher.builder()
                .person(Person.builder()
                        .name(teacherCreationDto.getName())
                        .phone(teacherCreationDto.getPhone())
                        .email(teacherCreationDto.getEmail())
                        .address(teacherCreationDto.getAddress())
                        .dob(teacherCreationDto.getDob())
                        .build())
                .title(teacherCreationDto.getTitle())
                .department(teacherCreationDto.getDepartment())
                .build();
    }

    public TeacherResponseDto toDto(Teacher savedTeacher) {
        return TeacherResponseDto.builder()
                .name(savedTeacher.getPerson().getName())
                .phone(savedTeacher.getPerson().getPhone())
                .email(savedTeacher.getPerson().getEmail())
                .address(savedTeacher.getPerson().getAddress())
                .dob(savedTeacher.getPerson().getDob())
                .tile(savedTeacher.getTitle())
                .department(savedTeacher.getDepartment())
                .build();
    }
}
