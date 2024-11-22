package com.jobsage.repository;

import com.jobsage.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByAdminId(Long adminId);
}