package com.java.be_starter.repository;

import com.java.be_starter.entity.CreditClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditClassRepository extends CrudRepository<CreditClass, Long>, PagingAndSortingRepository<CreditClass, Long> {
}
