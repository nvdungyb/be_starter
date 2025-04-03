package com.java.be_starter.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CreditClassResponseDto {
    private long teacherId;
    private long subjectId;
    private Date startTime;
    private Date endTime;
    private String room;
    private Integer totalStudents;
}
