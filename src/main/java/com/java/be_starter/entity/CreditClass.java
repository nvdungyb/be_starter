package com.java.be_starter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class CreditClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotNull(message = "Start time can not be null")
    private Date startTime;

    @NotNull(message = "End time can not be null")
    private Date endTime;

    @NotBlank(message = "Room can not be null")
    private String room;

    @NotBlank(message = "Total students can not be null")
    private Integer totalStudents;
}
