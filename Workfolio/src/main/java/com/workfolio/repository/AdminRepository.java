package com.workfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workfolio.entity.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByCompanyId(String companyId);
}