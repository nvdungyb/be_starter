package com.java.be_starter.controller.documents;

import com.java.be_starter.dto.request.StudentCreationDto;
import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.dto.response.ApiResponse;
import com.java.be_starter.entity.documents.StudentMongo;
import com.java.be_starter.service.documents.StudentMongoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class StudentDocumentController {
    private final StudentMongoService studentService;

    public StudentDocumentController(StudentMongoService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/api/documents/students")
    public ResponseEntity<ApiResponse<?>> createStudentDocument(@RequestBody StudentCreationDto studentCreationDto, HttpServletRequest request) {
        StudentMongo student = studentService.createStudent(studentCreationDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(student)
                        .message("Student created successfully")
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/documents/students")
    public ResponseEntity<ApiResponse<?>> getFirstPageStudents(HttpServletRequest request) {
        return getStudentByPage(1, request);
    }

    @GetMapping("/api/documents/students/pages/{pageNo}")
    public ResponseEntity<ApiResponse<?>> getStudentByPage(@PathVariable Integer pageNo, HttpServletRequest request) {
        List<StudentMongo> studentMongoList = studentService.getStudentByPage(pageNo);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(studentMongoList)
                        .message("Student found")
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/documents/students/{id}")
    public ResponseEntity<ApiResponse<?>> getStudentById(@PathVariable("id") String studentId, HttpServletRequest request) {
        StudentMongo student = studentService.findStudentById(studentId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(student)
                        .message(String.format("Student with id %s found", studentId))
                        .path(request.getRequestURI())
                        .build());
    }

    @PutMapping("/api/documents/students/{id}")
    public ResponseEntity<ApiResponse<?>> updateStudent(@PathVariable("id") String studentId, @RequestBody StudentUpdateDto updateDto, HttpServletRequest request) {
        StudentMongo studentUpdated = studentService.updateStudent(studentId, updateDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(studentUpdated)
                        .message(String.format("Student with id %s is updated", studentId))
                        .path(request.getRequestURI())
                        .build());
    }

    @DeleteMapping("/api/documents/students/{id}")
    public ResponseEntity<ApiResponse<?>> deleteStudent(@PathVariable("id") String studentId, HttpServletRequest request) {
        studentService.deleteStudent(studentId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(true)
                        .message(String.format("Student with id %s is deleted", studentId))
                        .path(request.getRequestURI())
                        .build());
    }
}
