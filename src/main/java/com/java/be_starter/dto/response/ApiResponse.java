package com.java.be_starter.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data                           // Khong co Getter va Setter thi server khong tra ket qua cho client dc.
public class ApiResponse<T> {
    private String timestamp;
    private int status;
    private String message;
    private T data;
    private String path;
}
