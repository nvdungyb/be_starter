package com.java.be_starter.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubjectResponseDto {
    private String subjectName;
    private String description;
}
