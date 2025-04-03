package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.SubjectCreationDto;
import com.java.be_starter.dto.request.SubjectUpdateDto;
import com.java.be_starter.entity.Subject;
import com.java.be_starter.exceptions.EntityConfigException;
import com.java.be_starter.exceptions.InvalidEntityException;
import com.java.be_starter.repository.SubjectRepository;
import com.java.be_starter.service.SubjectService;
import com.java.be_starter.utils.mapper.SubjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final int ELEMENTS_PER_PAGE = 10;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    public Subject createSubject(SubjectCreationDto subjectCreationDto) {
        Subject subject = subjectMapper.toEntity(subjectCreationDto);

        Subject savedSubject;
        try {
            savedSubject = subjectRepository.save(subject);
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException(e);
        } catch (OptimisticEntityLockException e) {
            throw new EntityConfigException(e);
        }
        return savedSubject;
    }

    public Subject updateSubject(long subjectId, SubjectUpdateDto subjectUpdateDto) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(subjectId)));

        subject.setSubjectName(subjectUpdateDto.getSubjectName() == null ? subject.getSubjectName() : subjectUpdateDto.getSubjectName());
        subject.setDescription(subjectUpdateDto.getDescription() == null ? subject.getDescription() : subjectUpdateDto.getDescription());
        return subjectRepository.save(subject);
    }

    public List<Subject> findByPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, ELEMENTS_PER_PAGE);
        Page<Subject> subjectPage = subjectRepository.findAll(pageable);
        return subjectPage.getContent();
    }

    // Đảm bảo không có cascade delete không mong muốn.
    @Transactional
    public void deleteSubject(long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}
