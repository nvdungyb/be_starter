package com.java.be_starter.repository.dao.impls;

import com.java.be_starter.entity.Person;
import com.java.be_starter.repository.dao.PersonDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;

@Component
public class PersonDaoImpl implements PersonDao {
    private final JdbcTemplate jdbcTemplate;

    public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Person savePerson(Person person) {
        String sql = "INSERT INTO person (address, dob, email, name, phone) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getAddress());
            ps.setDate(2, new Date(person.getDob().getTime()));
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getName());
            ps.setString(5, person.getPhone());
            return ps;
        }, keyHolder);

        person.setId(keyHolder.getKey().longValue());
        return person;
    }
}
