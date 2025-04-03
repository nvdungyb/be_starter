package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubjectCreationDto {
    private String subjectName;
    private String description;
}
