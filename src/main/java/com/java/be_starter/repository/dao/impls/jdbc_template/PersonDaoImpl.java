package com.java.be_starter.repository.dao.impls.jdbc_template;

import com.java.be_starter.entity.Person;
import com.java.be_starter.exceptions.SaveEntityException;
import com.java.be_starter.repository.dao.PersonDao;
import org.springframework.dao.DataAccessException;
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
        String email = person.getEmail();
        String phone = person.getPhone();
        try {
            if (!isUniqueEmail(person.getEmail())) {
                throw new SaveEntityException("Entity with email : " + email + " already exists!");
            }
            if (!isUniquePhoneNumber(phone)) {
                throw new SaveEntityException("Entity with phone number : " + phone + " already exists!");
            }

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
        } catch (DataAccessException e) {
            throw new SaveEntityException(e.getMessage());
        }
    }

    private boolean isUniqueEmail(String email) {
        return isUniqueField("email", email);
    }

    private boolean isUniquePhoneNumber(String phone) {
        return isUniqueField("phone", phone);
    }

    private boolean isUniqueField(String fieldName, String value) {
        String sql = "SELECT COUNT(*) FROM person WHERE " + fieldName + " = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, value);
        return count == 0;
    }

    @Transactional
    public Person executeUpdate(Person person) {
        try {
            String sql = "UPDATE person p SET name = ?, phone = ?, address = ?, dob = ? WHERE id = ?";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, person.getName());
                ps.setString(2, person.getPhone());
                ps.setString(3, person.getAddress());
                ps.setDate(4, new Date(person.getDob().getTime()));
                ps.setLong(5, person.getId());
                return ps;
            });

            return person;
        } catch (DataAccessException e) {
            throw new SaveEntityException(e.getMessage());
        }
    }
}
