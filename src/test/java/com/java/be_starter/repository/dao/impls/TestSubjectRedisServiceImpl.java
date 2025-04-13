package com.java.be_starter.repository.dao.impls;

import com.java.be_starter.entity.Subject;
import com.java.be_starter.redis.RedisService;
import com.java.be_starter.repository.SubjectRepository;
import com.java.be_starter.service.impls.SubjectRedisServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSubjectRedisServiceImpl {
    @Autowired
    private SubjectRedisServiceImpl subjectRedisService;
    @Autowired
    private SubjectRepository subjectRepository;
    private static final long subjectId = 22;
    @Autowired
    private RedisService redisService;

    @Test
    public void testSaveSubjectToCache() {
        Subject subject = subjectRepository.findById(subjectId).get();
        subjectRedisService.saveSubjectToCache(subject);
    }

    @Test
    public void testDeleteSubjectFromCache() {
        String key = subjectRedisService.keyGenerator(subjectId);
        redisService.delete(key);
        Subject subjectFromCache = redisService.get(key, Subject.class);
        Assertions.assertNull(subjectFromCache);
    }

    @Test
    public void testRetrieveSubjectFromCache() {
        testSaveSubjectToCache();
        Subject subjectFromCache = subjectRedisService.findSubjectById(subjectId);
        Assertions.assertNotNull(subjectFromCache);
    }

    @Test
    public void testRetrieveSubjectFromDatabase() {
        String key = subjectRedisService.keyGenerator(subjectId);
        redisService.delete(key);
        Subject subject = subjectRedisService.findSubjectById(subjectId);
        Assertions.assertNotNull(subject);
    }
}
