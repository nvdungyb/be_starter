package com.java.be_starter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Subject name can not be null!")
    private String subjectName;

    private String description;

    public Subject(long subjectId) {
        this.id = subjectId;
    }
}
