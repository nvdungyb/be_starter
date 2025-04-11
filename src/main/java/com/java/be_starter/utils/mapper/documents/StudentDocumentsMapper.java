package com.java.be_starter.utils.mapper.documents;

import com.java.be_starter.dto.request.documents.StudentCreationDocumentDto;
import com.java.be_starter.dto.response.PersonResponseDto;
import com.java.be_starter.dto.response.StudentResponseDto;
import com.java.be_starter.entity.documents.Address;
import com.java.be_starter.entity.documents.StudentMongo;
import org.springframework.stereotype.Component;

@Component
public class StudentDocumentsMapper {
    public StudentMongo toEntity(StudentCreationDocumentDto studentCreationDto) {
        return StudentMongo.builder()
                .major(studentCreationDto.getMajor())
                .name(studentCreationDto.getName())
                .phone(studentCreationDto.getPhone())
                .email(studentCreationDto.getEmail())
                .address(Address.builder()
                        .state(studentCreationDto.getState())
                        .city(studentCreationDto.getCity())
                        .street(studentCreationDto.getStreet())
                        .build())
                .dob(studentCreationDto.getDob())
                .year(studentCreationDto.getYear())
                .build();
    }

    public StudentResponseDto toDto(StudentMongo student) {
        return StudentResponseDto.builder()
                .personResponseDto(PersonResponseDto.builder()
                        .name(student.getName())
                        .phone(student.getPhone())
                        .email(student.getEmail())
                        .address(String.valueOf(student.getAddress()))
                        .dob(student.getDob())
                        .build())
                .major(student.getMajor())
                .year(student.getYear())
                .build();
    }
}
