package com.java.be_starter.service;

import com.java.be_starter.dto.request.CreditClassCreationDto;
import com.java.be_starter.dto.request.CreditClassUpdateDto;
import com.java.be_starter.entity.CreditClass;

import java.util.List;

public interface CreditClassService {
    CreditClass createCreditClass(CreditClassCreationDto dto);

    CreditClass updateClass(long id, CreditClassUpdateDto dto);

    void deleteClass(long id);

    List<CreditClass> findClassByPage(int pageNot);
}
