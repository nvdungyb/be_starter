package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class StudentClassCreationDto {
    private long studentId;
    private long classId;
    private Date registrationDate;
}
