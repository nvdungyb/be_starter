package com.java.be_starter.exceptions;

import com.java.be_starter.dto.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = EntityConfigException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityConfigException(final EntityConfigException exception) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = InvalidEntityException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidEntityException(final InvalidEntityException exception) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(final EntityNotFoundException exception) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }
}
