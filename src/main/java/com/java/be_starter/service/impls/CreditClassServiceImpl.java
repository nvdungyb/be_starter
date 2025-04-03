package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.CreditClassCreationDto;
import com.java.be_starter.dto.request.CreditClassUpdateDto;
import com.java.be_starter.entity.CreditClass;
import com.java.be_starter.exceptions.EntityConfigException;
import com.java.be_starter.exceptions.InvalidEntityException;
import com.java.be_starter.repository.CreditClassRepository;
import com.java.be_starter.service.CreditClassService;
import com.java.be_starter.utils.mapper.CreditClassMapper;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditClassServiceImpl implements CreditClassService {
    private final CreditClassRepository creditClassRepository;
    private final CreditClassMapper creditClassMapper;
    private static final int ELEMENTS_PER_PAGE = 10;

    public CreditClassServiceImpl(CreditClassRepository creditClassRepository, CreditClassMapper creditClassMapper) {
        this.creditClassRepository = creditClassRepository;
        this.creditClassMapper = creditClassMapper;
    }

    @Transactional
    public CreditClass createCreditClass(CreditClassCreationDto creditClassCreationDto) {
        CreditClass creditClass = creditClassMapper.toEntity(creditClassCreationDto);

        CreditClass savedCreditClass;
        try {
            savedCreditClass = saveClass(creditClass);
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException(e);
        } catch (OptimisticEntityLockException e) {
            throw new EntityConfigException(e);
        }
        return savedCreditClass;
    }

    @Transactional
    public CreditClass updateClass(long classId, CreditClassUpdateDto creditClassUpdateDto) {
        CreditClass creditClass = creditClassRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(classId)));

        creditClass.setRoom(creditClassUpdateDto.getRoom() == null ? creditClass.getRoom() : creditClassUpdateDto.getRoom());
        creditClass.setStartTime(creditClassUpdateDto.getStartTime() == null ? creditClass.getStartTime() : creditClassUpdateDto.getStartTime());
        creditClass.setEndTime(creditClassUpdateDto.getEndTime() == null ? creditClass.getEndTime() : creditClassUpdateDto.getEndTime());
        creditClass.setRoom(creditClassUpdateDto.getRoom() == null ? creditClass.getRoom() : creditClassUpdateDto.getRoom());
        creditClass.setTotalStudents(creditClassUpdateDto.getTotalStudents() == null ? creditClass.getTotalStudents() : creditClassUpdateDto.getTotalStudents());

        return saveClass(creditClass);
    }

    private CreditClass saveClass(CreditClass creditClass) {
        return creditClassRepository.save(creditClass);
    }

    @Transactional
    public void deleteClass(long classId) {
        creditClassRepository.deleteById(classId);
    }

    public List<CreditClass> findClassByPage(int pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, ELEMENTS_PER_PAGE);
        Page<CreditClass> creditClassPage = creditClassRepository.findAll(pageRequest);
        return creditClassPage.getContent();
    }
}
