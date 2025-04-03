package com.java.be_starter.repository;

import com.java.be_starter.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long>, PagingAndSortingRepository<Teacher, Long> {
}
