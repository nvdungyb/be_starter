package com.java.be_starter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Min(value = 1, message = "Total students can not be less than 1")
    @Max(value = 110, message="Total students can not be greater than 110")
    private Integer totalStudents;

    @Version
    private long version;
}
