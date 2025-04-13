package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.StudentUpdateDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Student;
import com.java.be_starter.exceptions.SaveEntityException;
import com.java.be_starter.redis.RedisService;
import com.java.be_starter.repository.StudentRepository;
import com.java.be_starter.utils.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class StudentRedisServiceImpl {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final RedisService redisService;
    private final static int ELEMENTS_PER_PAGE = 10;
    private final String cacheName = "studentCache";
    private final Duration duration = Duration.ofMinutes(5);

    public StudentRedisServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, RedisService redisService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.redisService = redisService;
    }

    private Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long studentId, StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(studentId)));

        student.setYear(studentUpdateDto.getYear() != null ? studentUpdateDto.getYear() : student.getYear());
        student.setMajor(studentUpdateDto.getMajor() != null ? studentUpdateDto.getMajor() : student.getMajor());
        Person person = student.getPerson();
        person.setName(studentUpdateDto.getName() == null ? person.getName() : studentUpdateDto.getName());
        person.setPhone(studentUpdateDto.getPhone() == null ? person.getPhone() : studentUpdateDto.getPhone());
        person.setAddress(studentUpdateDto.getAddress() == null ? person.getAddress() : studentUpdateDto.getAddress());
        person.setDob(studentUpdateDto.getDob() == null ? person.getDob() : studentUpdateDto.getDob());
        student.setPerson(person);

        Student savedStudent;
        try {
            savedStudent = saveStudent(student);
        } catch (Exception e) {
            throw new SaveEntityException(String.format("Error saving student %s", studentId), e);
        }

        saveStudentToCache(student);

        return savedStudent;
    }

    public void saveStudentToCache(Student student) {
        redisService.save(keyGenerator(student.getStudentId()), student, duration);
    }

    public String keyGenerator(Long studentId) {
        return String.format("%s::%s", cacheName, studentId);
    }

    public Student findStudentById(Long studentId) {
        Student student = redisService.get(keyGenerator(studentId), Student.class);
        if (student != null) {
            log.info("Found student {} in cache", student);
            return student;
        }

        log.info("Fetching student from database for id {}", studentId);
        return studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException(String.format("Student with id %s not found ", studentId)));
    }
}