package com.java.be_starter.controller;

import com.java.be_starter.dto.request.CreditClassCreationDto;
import com.java.be_starter.dto.request.CreditClassUpdateDto;
import com.java.be_starter.dto.response.ApiResponse;
import com.java.be_starter.dto.response.CreditClassResponseDto;
import com.java.be_starter.entity.CreditClass;
import com.java.be_starter.service.impls.CreditClassServiceImpl;
import com.java.be_starter.utils.mapper.CreditClassMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

// todo: chua test api nao ca
@RestController
public class CreditClassController {
    private final CreditClassServiceImpl creditClassService;
    private final CreditClassMapper creditClassMapper;

    public CreditClassController(CreditClassServiceImpl creditClassService, CreditClassMapper creditClassMapper) {
        this.creditClassService = creditClassService;
        this.creditClassMapper = creditClassMapper;
    }

    @PostMapping("/api/classes")
    public ResponseEntity<ApiResponse> createCreditClass(@RequestBody CreditClassCreationDto creditClassCreationDto, HttpServletRequest request) {

        CreditClass savedCreditClass = creditClassService.createCreditClass(creditClassCreationDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Credit class created successfully!")
                        .data(creditClassMapper.toDto(savedCreditClass))
                        .path(request.getRequestURI())
                        .build());
    }

    @PutMapping("/api/classes/{id}")
    public ResponseEntity<ApiResponse<?>> updateCreditClass(@PathVariable("id") long classId, @RequestBody CreditClassUpdateDto creditClassUpdateDto,
                                                            HttpServletRequest request) {
        CreditClass updatedClass = creditClassService.updateClass(classId, creditClassUpdateDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Credit class updated successfully!")
                        .data(creditClassMapper.toDto(updatedClass))
                        .path(request.getRequestURI())
                        .build());
    }

    @DeleteMapping("/api/classes/{id}")
    public ResponseEntity<ApiResponse<?>> deleteClass(@PathVariable("id") long classId, HttpServletRequest request) {

        creditClassService.deleteClass(classId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Credit class deleted successfully!")
                        .data(true)
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/classes")
    public ResponseEntity<ApiResponse<?>> getFirstPageClass(@PathVariable("id") long classId, HttpServletRequest request) {
        return getClassByPage(1, request);
    }

    @GetMapping("/api/classes/pages/{pageNo}")
    public ResponseEntity<ApiResponse<?>> getClassByPage(@PathVariable("pageNo") int pageNo, HttpServletRequest request) {

        List<CreditClass> classList = creditClassService.findClassByPage(pageNo);
        List<CreditClassResponseDto> classResponseDtos = classList.stream()
                .map(creditClassMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Credit class deleted successfully!")
                        .data(classResponseDtos)
                        .path(request.getRequestURI())
                        .build());
    }
}
