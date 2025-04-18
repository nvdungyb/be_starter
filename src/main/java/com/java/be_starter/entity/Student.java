package com.java.be_starter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private Person person;

    @NotBlank(message = "Major can not be null")
    private String major;

    @NotNull(message = "Year can not be null")
    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 6, message = "Year must be at most 6")
    private Integer year;

//    @Version
//    private long version;

    public Student(long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "major='" + major + '\'' +
                ", studentId=" + studentId +
                ", person=" + person +
                ", year=" + year +
                '}';
    }
}
