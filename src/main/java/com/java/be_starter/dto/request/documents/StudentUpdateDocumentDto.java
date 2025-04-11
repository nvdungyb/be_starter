package com.java.be_starter.dto.request.documents;

import com.java.be_starter.dto.UpdateDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StudentUpdateDocumentDto implements UpdateDto {
    private String name;
    private String phone;
    private Date dob;
    private String major;
    private Integer year;
    private String street;
    private String city;
    private String state;
}
