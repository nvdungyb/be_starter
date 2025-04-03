package com.java.be_starter.utils.mapper;

import com.java.be_starter.dto.request.CreditClassCreationDto;
import com.java.be_starter.dto.response.CreditClassResponseDto;
import com.java.be_starter.entity.CreditClass;
import com.java.be_starter.entity.Subject;
import com.java.be_starter.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class CreditClassMapper {
    public CreditClass toEntity(CreditClassCreationDto creditClassCreationDto) {
        return CreditClass.builder()
                .teacher(new Teacher(creditClassCreationDto.getTeacherId()))
                .subject(new Subject(creditClassCreationDto.getSubjectId()))
                .startTime(creditClassCreationDto.getStartTime())
                .endTime(creditClassCreationDto.getEndTime())
                .room(creditClassCreationDto.getRoom())
                .totalStudents(creditClassCreationDto.getTotalStudents())
                .build();
    }

    public CreditClassResponseDto toDto(CreditClass creditClass) {
        return CreditClassResponseDto.builder()
                .teacherId(creditClass.getTeacher().getId())
                .subjectId(creditClass.getSubject().getId())
                .startTime(creditClass.getStartTime())
                .endTime(creditClass.getEndTime())
                .room(creditClass.getRoom())
                .totalStudents(creditClass.getTotalStudents())
                .build();
    }
}
