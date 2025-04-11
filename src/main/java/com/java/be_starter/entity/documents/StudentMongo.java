package com.java.be_starter.entity.documents;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "student")
@Builder
@Data
public class StudentMongo {
    @Id
    private String id;

    @NotBlank
    private int year;

    @NotBlank
    private String major;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    @Indexed(unique = true)
    private String email;
    private Date dob;

    @Embedded
    private Address address;

    @Override
    public String toString() {
        return "StudentMongo{" +
                "address=" + address +
                ", id='" + id + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
/***
 * Khi id kiểu String: Mongo sẽ tự động sinh ObjectId và gán vào _id
 * Nếu để id là long: thì phải tự sinh id thủ công.
 */
