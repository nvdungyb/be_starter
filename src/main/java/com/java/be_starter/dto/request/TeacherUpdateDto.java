package com.java.be_starter.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherUpdateDto {
    private String name;
    private String phone;
    private String address;
    private Date dob;
    private String title;
    private String department;
}
