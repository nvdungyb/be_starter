package com.java.be_starter.repository.documents;

import com.java.be_starter.entity.documents.StudentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMongoRepository extends MongoRepository<StudentMongo, String>, PagingAndSortingRepository<StudentMongo, String> {
}
