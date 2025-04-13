package com.java.be_starter.controller;

import com.java.be_starter.dto.request.TeacherCreationDto;
import com.java.be_starter.dto.request.TeacherUpdateDto;
import com.java.be_starter.dto.response.ApiResponse;
import com.java.be_starter.dto.response.TeacherResponseDto;
import com.java.be_starter.entity.Teacher;
import com.java.be_starter.service.impls.TeacherServiceImpl;
import com.java.be_starter.utils.mapper.TeacherMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TeacherController {
    private final TeacherServiceImpl teacherService;
    private final TeacherMapper teacherMapper;

    public TeacherController(TeacherServiceImpl teacherService, TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @PostMapping("/api/teachers")
    public ResponseEntity<ApiResponse<?>> saveTeacher(@RequestBody TeacherCreationDto teacherCreationDto, HttpServletRequest request) {

        Teacher savedTeacher = teacherService.createTeacher(teacherCreationDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(teacherMapper.toDto(savedTeacher))
                        .message("Teacher saved successfully!")
                        .path(request.getRequestURI())
                        .build());
    }

    @PutMapping("/api/teachers/{id}")
    public ResponseEntity<ApiResponse<?>> updateTeacher(@RequestBody TeacherUpdateDto teacherUpdateDto, @PathVariable("id") long teacherId, HttpServletRequest request) {

        Teacher updatedTeacher = teacherService.updateTeacher(teacherId, teacherUpdateDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(teacherMapper.toDto(updatedTeacher))
                        .message("Teacher updated successfully!")
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/teachers/{id}")
    public ResponseEntity<ApiResponse<?>> getTeacherById(@PathVariable("id") long teacherId, HttpServletRequest request) {

        Teacher teacher = teacherService.findTeacherById(teacherId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(teacherMapper.toDto(teacher))
                        .message("Get teacher successfully!")
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/teachers")
    public ResponseEntity<ApiResponse<?>> getAllTeachers(HttpServletRequest request) {
        return getTeacherByPage(1, request);
    }

    @GetMapping("/api/teachers/pages/{pageNo}")
    public ResponseEntity<ApiResponse<?>> getTeacherByPage(@PathVariable("pageNo") int pageNo, HttpServletRequest request) {
        List<Teacher> teacherList = teacherService.findTeacherByPage(pageNo);

        List<TeacherResponseDto> teacherResponseDtoList = teacherList.stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .data(teacherResponseDtoList)
                        .message("Teacher list successfully!")
                        .path(request.getRequestURI())
                        .build());
    }
}
