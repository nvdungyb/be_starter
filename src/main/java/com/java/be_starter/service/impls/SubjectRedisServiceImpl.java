package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.SubjectUpdateDto;
import com.java.be_starter.entity.Subject;
import com.java.be_starter.exceptions.SaveEntityException;
import com.java.be_starter.redis.RedisService;
import com.java.be_starter.repository.SubjectRepository;
import com.java.be_starter.utils.mapper.SubjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
public class SubjectRedisServiceImpl {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final int ELEMENTS_PER_PAGE = 10;
    private final RedisService redisService;
    private final String cacheName = "subjectCache";
    private final Duration duration = Duration.ofMinutes(10);

    public SubjectRedisServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper, RedisService redisService) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.redisService = redisService;
    }

    public Subject updateSubject(long subjectId, SubjectUpdateDto subjectUpdateDto) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(subjectId)));

        subject.setSubjectName(subjectUpdateDto.getSubjectName() == null ? subject.getSubjectName() : subjectUpdateDto.getSubjectName());
        subject.setDescription(subjectUpdateDto.getDescription() == null ? subject.getDescription() : subjectUpdateDto.getDescription());

        Subject updatedSubject;
        try {
            updatedSubject = subjectRepository.save(subject);
        } catch (Exception e) {
            throw new SaveEntityException(String.format("Failed to save subject %s", subjectUpdateDto.getSubjectName()), e);
        }

        saveSubjectToCache(subject);

        return updatedSubject;
    }

    public void saveSubjectToCache(Subject subject) {
        redisService.save(keyGenerator(subject.getId()), subject, duration);
    }

    public String keyGenerator(long subjectId) {
        return String.format("%s::%s", "subjectCache", subjectId);
    }

    @Transactional
    public void deleteSubject(long subjectId) {
        subjectRepository.deleteById(subjectId);
        redisService.delete(keyGenerator(subjectId));
    }

    public Subject findSubjectById(long subjectId) {
        Subject subjectCached = redisService.get(keyGenerator(subjectId), Subject.class);
        if (subjectCached != null) {
            log.info("Found subject {} in cache", subjectCached.getSubjectName());
            return subjectCached;
        }

        log.info("Retrieving subject with id {} in database", subjectId);
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subject with id %s not found", subjectId)));
    }
}
