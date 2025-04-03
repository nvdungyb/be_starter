package com.java.be_starter.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TeacherResponseDto {
    private String name;
    private String phone;
    private String email;
    private String address;
    private Date dob;
    private String tile;
    private String department;
}
