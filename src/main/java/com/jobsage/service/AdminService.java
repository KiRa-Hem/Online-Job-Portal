package com.jobsage.service;

import com.jobsage.dto.AdminRegisterRequest;
import com.jobsage.dto.AuthResponse;
import com.jobsage.dto.JobRequest;
import com.jobsage.model.*;
import com.jobsage.repository.AdminRepository;
import com.jobsage.repository.JobRepository;
import com.jobsage.repository.JobApplicationRepository;
import com.jobsage.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public void register(AdminRegisterRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        Admin admin = new Admin();
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setCompanyName(request.getCompanyName());

        adminRepository.save(admin);
    }

    @Transactional
    public Job postJob(Long adminId, JobRequest request) {
        Admin admin = adminRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin not found"));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setBenefits(request.getBenefits());
        job.setLocation(request.getLocation());
        job.setType(request.getType());
        job.setSalary(request.getSalary());
        job.setAdmin(admin);

        return jobRepository.save(job);
    }

    public List<Job> getAdminJobs(Long adminId) {
        return jobRepository.findByAdminId(adminId);
    }

    public List<JobApplication> getJobApplications(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    @Transactional
    public void updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setStatus(status);
        jobApplicationRepository.save(application);
    }
}