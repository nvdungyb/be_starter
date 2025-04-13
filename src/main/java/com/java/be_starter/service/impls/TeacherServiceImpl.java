package com.java.be_starter.service.impls;

import com.java.be_starter.dto.request.TeacherCreationDto;
import com.java.be_starter.dto.request.TeacherUpdateDto;
import com.java.be_starter.entity.Person;
import com.java.be_starter.entity.Teacher;
import com.java.be_starter.exceptions.EntityConfigException;
import com.java.be_starter.exceptions.InvalidEntityException;
import com.java.be_starter.repository.TeacherRepository;
import com.java.be_starter.service.TeacherService;
import com.java.be_starter.utils.mapper.TeacherMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final int ELEMENTS_PER_PAGE = 10;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public Teacher createTeacher(TeacherCreationDto teacherCreationDto) {
        Teacher teacher = teacherMapper.toEntity(teacherCreationDto);

        Teacher savedTeacher;
        try {
            savedTeacher = saveTeacher(teacher);
        } catch (IllegalArgumentException e) {
            throw new EntityConfigException(e);
        } catch (OptimisticEntityLockException e) {
            throw new InvalidEntityException(e);
        }
        return savedTeacher;
    }

    @CachePut(value = "teacherCache", key = "#teacherId")
    public Teacher updateTeacher(long teacherId, TeacherUpdateDto teacherUpdateDto) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(teacherId)));

        teacher.setTitle(teacherUpdateDto.getTitle() != null ? teacherUpdateDto.getTitle() : teacher.getTitle());
        teacher.setDepartment(teacherUpdateDto.getDepartment() != null ? teacherUpdateDto.getDepartment() : teacher.getDepartment());
        Person person = teacher.getPerson();
        person.setName(teacherUpdateDto.getName() != null ? teacherUpdateDto.getName() : person.getName());
        person.setPhone(teacherUpdateDto.getPhone() != null ? teacherUpdateDto.getPhone() : person.getPhone());
        person.setAddress(teacherUpdateDto.getAddress() != null ? teacherUpdateDto.getAddress() : person.getAddress());
        person.setDob(teacherUpdateDto.getDob() != null ? teacherUpdateDto.getDob() : person.getDob());
        teacher.setPerson(person);

        return saveTeacher(teacher);
    }

    private Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> findTeacherByPage(int pageNo) {
        Pageable page = PageRequest.of(pageNo - 1, ELEMENTS_PER_PAGE);
        Page<Teacher> pageTeacher = teacherRepository.findAll(page);
        return pageTeacher.getContent();
    }

    @Cacheable(value = "teacherCache", key = "#teacherId")
    public Teacher findTeacherById(long teacherId) {
        log.info("Retrieve teacher with id {} in database", teacherId);
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher with id %s not found", teacherId)));
    }
}
