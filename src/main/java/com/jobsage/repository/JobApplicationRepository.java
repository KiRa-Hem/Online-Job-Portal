package com.jobsage.repository;

import com.jobsage.model.JobApplication;
import com.jobsage.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findByUserId(Long userId);
    List<JobApplication> findByJobIdAndStatus(Long jobId, ApplicationStatus status);
}