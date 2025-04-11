package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class StudentCreationDto {
    private String name;
    private String phone;
    private String email;
    private String address;
    private Date dob;
    private String major;
    private int year;
}
