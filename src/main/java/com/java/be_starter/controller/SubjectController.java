package com.java.be_starter.controller;

import com.java.be_starter.dto.request.SubjectCreationDto;
import com.java.be_starter.dto.request.SubjectUpdateDto;
import com.java.be_starter.dto.response.ApiResponse;
import com.java.be_starter.dto.response.SubjectResponseDto;
import com.java.be_starter.entity.Subject;
import com.java.be_starter.service.impls.SubjectServiceImpl;
import com.java.be_starter.utils.mapper.SubjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SubjectController {
    private final SubjectServiceImpl subjectService;
    private final SubjectMapper subjectMapper;

    public SubjectController(SubjectServiceImpl subjectService, SubjectMapper subjectMapper) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
    }

    @PostMapping("/api/subjects")
    public ResponseEntity<ApiResponse<?>> createSubject(@RequestBody SubjectCreationDto subjectCreationDto, HttpServletRequest request) {

        Subject savedSubject = subjectService.createSubject(subjectCreationDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Subject created")
                        .data(subjectMapper.toDto(savedSubject))
                        .path(request.getRequestURI())
                        .build());
    }

    @PutMapping("/api/subjects/{id}")
    public ResponseEntity<ApiResponse<?>> updateSubject(@PathVariable("id") long subjectId, @RequestBody SubjectUpdateDto subjectUpdateDto
            , HttpServletRequest request) {
        Subject updatedSubject = subjectService.updateSubject(subjectId, subjectUpdateDto);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Subject updated")
                        .data(subjectMapper.toDto(updatedSubject))
                        .path(request.getRequestURI())
                        .build());
    }

    @GetMapping("/api/subjects")
    public ResponseEntity<ApiResponse<?>> getFirsPageSubjects(HttpServletRequest request) {
        return getSubjectsByPage(1, request);
    }

    @GetMapping("/api/subjects/pages/{pageNo}")
    public ResponseEntity<ApiResponse<?>> getSubjectsByPage(@PathVariable("pageNo") int pageNo, HttpServletRequest request) {
        List<Subject> subjectList = subjectService.findByPage(pageNo);

        List<SubjectResponseDto> subjectResponseDtos = subjectList.stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Subjects found")
                        .data(subjectResponseDtos)
                        .path(request.getRequestURI())
                        .build());
    }

    @DeleteMapping("/api/subjects/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSubject(@PathVariable("id") long subjectId, HttpServletRequest request) {

        subjectService.deleteSubject(subjectId);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .status(200)
                        .message("Subject deleted")
                        .data(true)
                        .path(request.getRequestURI())
                        .build());
    }
}