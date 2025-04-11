package com.java.be_starter.repository.dao.impls.mongo_template;

import com.java.be_starter.dto.request.documents.StudentCreationDocumentDto;
import com.java.be_starter.dto.request.documents.StudentUpdateDocumentDto;
import com.java.be_starter.entity.documents.Address;
import com.java.be_starter.entity.documents.StudentMongo;
import com.java.be_starter.exceptions.SaveEntityException;
import com.java.be_starter.service.StudentService;
import com.java.be_starter.utils.mapper.documents.StudentDocumentsMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentMongoDaoImpl implements StudentService<StudentMongo, StudentCreationDocumentDto, StudentUpdateDocumentDto, String> {
    private final MongoTemplate mongoTemplate;
    private final StudentDocumentsMapper studentDocumentsMapper;
    private final int ELEMENTS_PER_PAGE = 5;

    public StudentMongoDaoImpl(MongoTemplate mongoTemplate, StudentDocumentsMapper studentDocumentsMapper) {
        this.mongoTemplate = mongoTemplate;
        this.studentDocumentsMapper = studentDocumentsMapper;
    }

    @Override
    public StudentMongo createStudent(StudentCreationDocumentDto studentCreationDto) {
        if (isEmailExists(studentCreationDto.getEmail())) {
            throw new SaveEntityException(String.format("Email %s already exists", studentCreationDto.getEmail()));
        }

        if (isPhoneExists(studentCreationDto.getPhone())) {
            throw new SaveEntityException(String.format("Phone %s already exists", studentCreationDto.getPhone()));
        }

        StudentMongo studentMongo = studentDocumentsMapper.toEntity(studentCreationDto);

        return mongoTemplate.insert(studentMongo);
    }

    private boolean isPhoneExists(String phone) {
        Query query = Query.query(Criteria.where("phone").is(phone));
        return mongoTemplate.exists(query, StudentMongo.class);
    }

    private boolean isEmailExists(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.exists(query, StudentMongo.class);
    }

    @Override
    public StudentMongo updateStudent(String studentId, StudentUpdateDocumentDto studentUpdateDto) {
        StudentMongo studentMongo = findById(studentId);
        if (studentMongo == null) {
            throw new EntityNotFoundException(String.format("Student with id : %s not found", studentId));
        }

        studentMongo.setName(valueOrDefault(studentMongo.getName(), studentUpdateDto.getName()));
        studentMongo.setPhone(valueOrDefault(studentMongo.getPhone(), studentUpdateDto.getPhone()));
        studentMongo.setDob(valueOrDefault(studentMongo.getDob(), studentUpdateDto.getDob()));
        studentMongo.setMajor(valueOrDefault(studentMongo.getMajor(), studentUpdateDto.getMajor()));
        studentMongo.setYear(valueOrDefault(studentMongo.getYear(), studentUpdateDto.getYear()));

        if (!(studentUpdateDto.getCity() == null || studentUpdateDto.getState() == null || studentUpdateDto.getStreet() == null)) {
            studentMongo.setAddress(Address.builder().city(studentUpdateDto.getCity()).street(studentUpdateDto.getStreet()).state(studentUpdateDto.getState()).build());
        }

        return executeUpdate(studentMongo);
    }

    private StudentMongo executeUpdate(StudentMongo studentMongo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(studentMongo.getId()));
        Update update = new Update().set("name", studentMongo.getName()).set("phone", studentMongo.getPhone()).set("dob", studentMongo.getDob()).set("major", studentMongo.getMajor()).set("year", studentMongo.getYear()).set("address", studentMongo.getAddress());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return mongoTemplate.findAndModify(query, update, options, StudentMongo.class);
    }

    @Override
    public List<StudentMongo> findStudentByPage(int pageNo) {
        int skip = (pageNo - 1) * ELEMENTS_PER_PAGE;
        Query query = new Query();
        query.skip(skip);
        query.limit(ELEMENTS_PER_PAGE);

        return mongoTemplate.find(query, StudentMongo.class);
    }

    public boolean existsById(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return mongoTemplate.exists(query, StudentMongo.class);
    }

    public StudentMongo findById(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, StudentMongo.class);
    }

    public void deleteById(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, StudentMongo.class);
    }

    public <T> T valueOrDefault(T newValue, T oldValue) {
        return newValue == null ? oldValue : newValue;
    }
}
