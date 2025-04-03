package com.java.be_starter.utils.mapper;

import com.java.be_starter.dto.request.SubjectCreationDto;
import com.java.be_starter.dto.response.SubjectResponseDto;
import com.java.be_starter.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    public Subject toEntity(SubjectCreationDto subjectCreationDto) {
        return Subject.builder()
                .subjectName(subjectCreationDto.getSubjectName())
                .description(subjectCreationDto.getDescription())
                .build();
    }

    public SubjectResponseDto toDto(Subject subject) {
        return SubjectResponseDto.builder()
                .subjectName(subject.getSubjectName())
                .description(subject.getDescription())
                .build();
    }
}
