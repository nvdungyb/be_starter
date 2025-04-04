package com.java.be_starter.repository;

import com.java.be_starter.entity.StudentClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassRepository extends CrudRepository<StudentClass, Long>, PagingAndSortingRepository<StudentClass, Long> {
    boolean existsByStudent_StudentIdAndCreditClass_Id(long studentStudentId, long creditClassId);
}
