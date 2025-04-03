package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreditClassCreationDto {
    private long teacherId;
    private long subjectId;
    private Date startTime;
    private Date endTime;
    private String room;
    private Integer totalStudents;
}
