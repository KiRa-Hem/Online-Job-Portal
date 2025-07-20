package com.workfolio.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workfolio.entity.Job;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByTitleContaining(String title);
}