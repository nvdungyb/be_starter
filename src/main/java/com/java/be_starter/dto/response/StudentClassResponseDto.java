package com.java.be_starter.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class StudentClassResponseDto {
    private long studentId;
    private long classId;
    private Date registrationDate;
}
