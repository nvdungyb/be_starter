package com.java.be_starter.repository.documents;

import com.java.be_starter.entity.documents.PersonMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonMongoRepository extends MongoRepository<PersonMongo, Long> {
    PersonMongo findByEmail(String email);
}
