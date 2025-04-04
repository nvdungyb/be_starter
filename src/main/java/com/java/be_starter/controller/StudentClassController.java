package com.java.be_starter.controller;

import com.java.be_starter.dto.request.StudentClassCreationDto;
import com.java.be_starter.dto.response.ApiResponse;
import com.java.be_starter.dto.response.StudentClassResponseDto;
import com.java.be_starter.entity.StudentClass;
import com.java.be_starter.service.impls.StudentClassServiceImpl;
import com.java.be_starter.utils.mapper.StudentClassMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentClassController {
    private final StudentClassServiceImpl studentClassService;
    private final StudentClassMapper studentClassMapper;

    public StudentClassController(StudentClassServiceImpl studentClassService, StudentClassMapper studentClassMapper) {
        this.studentClassService = studentClassService;
        this.studentClassMapper = studentClassMapper;
    }

    @PostMapping("/api/classes/students")
    public ResponseEntity<ApiResponse<?>> createStudentClass(@RequestBody StudentClassCreationDto dto, HttpServletRequest request) {

        StudentClass savedStudentClass = studentClassService.createStudentClass(dto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(studentClassMapper.toDto(savedStudentClass))
                        .message("Student regis class successfully")
                        .path(request.getRequestURI())
                        .build());
    }

    @DeleteMapping("/api/classes/students/{id}")
    public ResponseEntity<ApiResponse<?>> deleteStudentClass(@PathVariable("id") long id, HttpServletRequest request) {

        studentClassService.deleteStudentClass(id);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(true)
                        .message("Student regis class successfully deleted")
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/classes/students")
    public ResponseEntity<ApiResponse<?>> getFirstPageStudentClasses(HttpServletRequest request) {
        return getStudentClassesByPage(1, request);
    }

    @GetMapping("/api/classes/students/pages/{pageNo}")
    public ResponseEntity<ApiResponse<?>> getStudentClassesByPage(@PathVariable("pageNo") int pageNo, HttpServletRequest request) {

        List<StudentClass> studentClassList = studentClassService.getStudentClassByPage(pageNo);
        List<StudentClassResponseDto> studentResponseDtoList = studentClassList.stream()
                .map(studentClassMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(studentResponseDtoList)
                        .message("Student regis class successfully retrieved")
                        .path(request.getRequestURI())
                        .build());
    }
}
