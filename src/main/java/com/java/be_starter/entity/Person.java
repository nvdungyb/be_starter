package com.java.be_starter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 50, message = "Name's length must be 2-50 characters.")
    private String name;

    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Phone number's length must be 10 digits.")
    @Column(nullable = false, unique = true)
    private String phone;

    @Email(message = "Invalid email!")
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    private String address;
    private Date dob;

    public Person(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
