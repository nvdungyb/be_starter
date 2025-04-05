package com.java.be_starter.utils.jdbc.mapper;

import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowMapper implements RowMapper<Student> {
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = Person.builder()
                .id(rs.getLong("person_id"))
                .name(rs.getString("name"))
                .address(rs.getString("address"))
                .dob(rs.getDate("dob"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .build();

        Student student = Student.builder()
                .studentId(rs.getLong("student_id"))
                .major(rs.getString("major"))
                .year(rs.getInt("year"))
                .person(person)
                .build();
        return student;
    }
}
