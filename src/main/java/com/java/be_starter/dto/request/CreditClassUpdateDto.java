package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CreditClassUpdateDto {
    private Date startTime;
    private Date endTime;
    private String room;
    private Integer totalStudents;
}
