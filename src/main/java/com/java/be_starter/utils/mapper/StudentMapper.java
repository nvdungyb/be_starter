package com.java.be_starter.utils.mapper;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.response.PersonResponseDto;
import com.java.be_starter.dto.response.StudentResponseDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toEntity(StudentCreationDto studentCreationDto) {
        return Student.builder()
                .major(studentCreationDto.getMajor())
                .person(Person.builder()
                        .name(studentCreationDto.getName())
                        .phone(studentCreationDto.getPhone())
                        .email(studentCreationDto.getEmail())
                        .address(studentCreationDto.getAddress())
                        .dob(studentCreationDto.getDob())
                        .build())
                .year(studentCreationDto.getYear())
                .build();
    }

    public StudentResponseDto toDto(Student student) {
        return StudentResponseDto.builder()
                .personResponseDto(PersonResponseDto.builder()
                        .name(student.getPerson().getName())
                        .phone(student.getPerson().getPhone())
                        .email(student.getPerson().getEmail())
                        .address(student.getPerson().getAddress())
                        .dob(student.getPerson().getDob())
                        .build())
                .major(student.getMajor())
                .year(student.getYear())
                .build();
    }
}
