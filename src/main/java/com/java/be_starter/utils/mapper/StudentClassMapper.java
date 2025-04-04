package com.java.be_starter.utils.mapper;

import com.java.be_starter.dto.request.StudentClassCreationDto;
import com.java.be_starter.dto.response.StudentClassResponseDto;
import com.java.be_starter.entity.CreditClass;
import com.java.be_starter.entity.Student;
import com.java.be_starter.entity.StudentClass;
import org.springframework.stereotype.Component;

@Component
public class StudentClassMapper {
    public StudentClass toEntity(StudentClassCreationDto dto) {
        return StudentClass.builder()
                .student(new Student(dto.getStudentId()))
                .creditClass(new CreditClass(dto.getClassId()))
                .registrationDate(dto.getRegistrationDate())
                .build();
    }

    public StudentClassResponseDto toDto(StudentClass studentClass) {
        return StudentClassResponseDto.builder()
                .classId(studentClass.getCreditClass().getId())
                .studentId(studentClass.getStudent().getStudentId())
                .registrationDate(studentClass.getRegistrationDate())
                .build();
    }
}
