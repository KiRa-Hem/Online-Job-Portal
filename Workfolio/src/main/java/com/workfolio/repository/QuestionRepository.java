package com.workfolio.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workfolio.entity.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByCompanyId(String companyId);
}