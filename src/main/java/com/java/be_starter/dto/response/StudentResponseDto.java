package com.java.be_starter.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentResponseDto {
    private PersonResponseDto personResponseDto;
    private String major;
    private int year;
}
