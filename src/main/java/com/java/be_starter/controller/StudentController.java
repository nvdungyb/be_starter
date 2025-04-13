package com.java.be_starter.controller;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.dto.response.ApiResponse;
import com.java.be_starter.dto.response.StudentResponseDto;
import com.java.be_starter.entity.Student;
import com.java.be_starter.service.impls.StudentServiceImpl;
import com.java.be_starter.utils.mapper.StudentMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final StudentServiceImpl studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentServiceImpl studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping("/api/students")
    public ResponseEntity<ApiResponse<?>> createStudent(@RequestBody StudentCreationDto studentCreationDto, HttpServletRequest request) {

        Student savedStudent = studentService.createStudent(studentCreationDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(studentMapper.toDto(savedStudent))
                        .message("Saved student successfully")
                        .path(request.getRequestURI())
                        .build()
                );
    }

    @PutMapping("/api/students/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody StudentUpdateDto studentUpdateDto, @PathVariable("id") Long studentId, HttpServletRequest request) {

        Student updatedStudent = studentService.updateStudent(studentId, studentUpdateDto);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .status(200)
                .data(studentMapper.toDto(updatedStudent))
                .message("Updated student successfully")
                .path(request.getRequestURI())
                .build());
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<ApiResponse<?>> getStudentById(@PathVariable("id") Long studentId, HttpServletRequest request) {

        Student student = studentService.findStudentById(studentId);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .status(200)
                .data(studentMapper.toDto(student))
                .message("Updated student successfully")
                .path(request.getRequestURI())
                .build());
    }

    @GetMapping("/api/students")
    public ResponseEntity<ApiResponse<?>> getFirstPageStudents(HttpServletRequest request) {
        return getStudentByPage(1, request);
    }

    @GetMapping("/api/students/pages/{pageId}")
    public ResponseEntity<ApiResponse<?>> getStudentByPage(@PathVariable("pageId") int pageNo, HttpServletRequest request) {
        List<Student> studentList = studentService.findStudentByPage(pageNo);

        List<StudentResponseDto> studentResponseDtoList = studentList.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(studentResponseDtoList)
                        .message("All students successfully")
                        .path(request.getRequestURI())
                        .build());
    }
}
