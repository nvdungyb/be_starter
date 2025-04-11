package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StudentUpdateDto {
    private String name;
    private String phone;
    private String address;
    private Date dob;
    private String major;
    private Integer year;
}
