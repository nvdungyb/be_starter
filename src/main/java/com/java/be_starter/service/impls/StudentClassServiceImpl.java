package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.StudentClassCreationDto;
import com.java.be_starter.entity.StudentClass;
import com.java.be_starter.exceptions.EntityConfigException;
import com.java.be_starter.exceptions.InvalidEntityException;
import com.java.be_starter.repository.CreditClassRepository;
import com.java.be_starter.repository.StudentClassRepository;
import com.java.be_starter.repository.StudentRepository;
import com.java.be_starter.service.StudentClassService;
import com.java.be_starter.utils.mapper.StudentClassMapper;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassServiceImpl implements StudentClassService {
    private final StudentClassRepository studentClassRepository;
    private final StudentClassMapper studentClassMapper;
    private final StudentRepository studentRepository;
    private final CreditClassRepository creditClassRepository;
    private final int ELEMENTS_PER_PAGE = 10;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository, StudentClassMapper studentClassMapper, StudentRepository studentRepository, CreditClassRepository creditClassRepository) {
        this.studentClassRepository = studentClassRepository;
        this.studentClassMapper = studentClassMapper;
        this.studentRepository = studentRepository;
        this.creditClassRepository = creditClassRepository;
    }

    public StudentClass createStudentClass(StudentClassCreationDto dto) {
        long studentId = dto.getStudentId();
        long classId = dto.getClassId();
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Student with id " + studentId + " not found");
        }
        if (!creditClassRepository.existsById(classId)) {
            throw new EntityNotFoundException("Class with id " + classId + " not found");
        }
        if (studentClassRepository.existsByStudent_StudentIdAndCreditClass_Id(studentId, classId)) {
            throw new RuntimeException("Student with id " + studentId + " already regis this credit class id: " + classId);
        }

        StudentClass studentClass = studentClassMapper.toEntity(dto);

        StudentClass savedStudentClass;
        try {
            savedStudentClass = saveStudentClass(studentClass);
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException(e);
        } catch (OptimisticEntityLockException e) {
            throw new EntityConfigException(e);
        }
        return savedStudentClass;
    }

    private StudentClass saveStudentClass(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }

    public void deleteStudentClass(long id) {
        studentClassRepository.deleteById(id);
    }

    public List<StudentClass> getStudentClassByPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, ELEMENTS_PER_PAGE);
        Page<StudentClass> page = studentClassRepository.findAll(pageable);

        return page.getContent();
    }
}
