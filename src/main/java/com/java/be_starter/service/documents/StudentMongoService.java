package com.java.be_starter.service.documents;

import com.java.be_starter.dto.request.documents.StudentCreationDocumentDto;
import com.java.be_starter.dto.request.documents.StudentUpdateDocumentDto;
import com.java.be_starter.entity.documents.Address;
import com.java.be_starter.entity.documents.StudentMongo;
import com.java.be_starter.repository.documents.StudentMongoRepository;
import com.java.be_starter.utils.mapper.documents.StudentDocumentsMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentMongoService {
    private final StudentMongoRepository studentMongoRepository;
    private final int ELEMENTS_PER_PATE = 5;
    private final StudentDocumentsMapper studentDocumentsMapper;

    public StudentMongoService(StudentMongoRepository studentMongoRepository, StudentDocumentsMapper studentDocumentsMapper) {
        this.studentMongoRepository = studentMongoRepository;
        this.studentDocumentsMapper = studentDocumentsMapper;
    }

    public StudentMongo createStudent(StudentCreationDocumentDto dto) {
        StudentMongo studentMongo = studentDocumentsMapper.toEntity(dto);

        return saveStudent(studentMongo);
    }

    private StudentMongo saveStudent(StudentMongo studentMongo) {
        return studentMongoRepository.save(studentMongo);
    }

    public List<StudentMongo> findAll() {
        return studentMongoRepository.findAll();
    }

    public StudentMongo findStudentById(String studentId) {
        Optional<StudentMongo> studentMongoOptional = studentMongoRepository.findById(studentId);
        return studentMongoOptional.orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " not found"));
    }

    public StudentMongo updateStudent(String studentId, StudentUpdateDocumentDto dto) {
        StudentMongo studentMongo = findStudentById(studentId);

        studentMongo.setName(dto.getName() == null ? studentMongo.getName() : dto.getName());
        studentMongo.setAddress(Address.builder()
                .street(dto.getStreet() == null ? studentMongo.getAddress().getStreet() : dto.getStreet())
                .city(dto.getCity() == null ? studentMongo.getAddress().getCity() : dto.getCity())
                .state(dto.getState() == null ? studentMongo.getAddress().getState() : dto.getState())
                .build());
        studentMongo.setDob(dto.getDob() == null ? studentMongo.getDob() : dto.getDob());
        studentMongo.setYear(dto.getYear() == null ? studentMongo.getYear() : dto.getYear());
        studentMongo.setPhone(dto.getPhone() == null ? studentMongo.getPhone() : dto.getPhone());
        studentMongo.setMajor(dto.getMajor() == null ? studentMongo.getMajor() : dto.getMajor());
        return saveStudent(studentMongo);
    }

    public void deleteStudent(String studentId) {
        studentMongoRepository.deleteById(studentId);
    }

    public List<StudentMongo> getStudentByPage(Integer pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, ELEMENTS_PER_PATE);
        return studentMongoRepository.findAll(pageRequest).getContent();
    }
}
