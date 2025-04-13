package com.java.be_starter.repository.dao.impls;

import com.java.be_starter.entity.Student;
import com.java.be_starter.redis.RedisService;
import com.java.be_starter.repository.StudentRepository;
import com.java.be_starter.service.impls.StudentRedisServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestStudentRedisServiceImpl {
    @Autowired
    private StudentRedisServiceImpl studentRedisService;
    @Autowired
    private StudentRepository studentRepository;
    private static final long studentId = 19;
    @Autowired
    private RedisService redisService;

    @Test
    public void testSaveStudentToCache() {
        Student student = studentRepository.findById(studentId).get();
        studentRedisService.saveStudentToCache(student);
    }

    @Test
    public void testFindStudentFromCache() {
        testSaveStudentToCache();
        Student studentFound = studentRedisService.findStudentById(studentId);
        System.out.println(studentFound);
        Assertions.assertNotNull(studentFound);
    }

    @Test
    public void testFindStudentInDatabase() {
        String key = studentRedisService.keyGenerator(studentId);
        redisService.delete(key);
        Student student = studentRedisService.findStudentById(studentId);
        assert student != null;
    }
}
