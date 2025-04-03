package com.java.be_starter.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectUpdateDto {
    private String subjectName;
    private String description;
}
