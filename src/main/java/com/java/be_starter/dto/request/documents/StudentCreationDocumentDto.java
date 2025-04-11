package com.java.be_starter.dto.request.documents;

import com.java.be_starter.dto.CreationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class StudentCreationDocumentDto implements CreationDto {
    private String name;
    private String phone;
    private String email;
    private Date dob;
    private String major;
    private int year;
    private String street;
    private String city;
    private String state;
}
