package com.jobsage.controller;

import com.jobsage.dto.JobRequest;
import com.jobsage.model.ApplicationStatus;
import com.jobsage.model.Job;
import com.jobsage.model.JobApplication;
import com.jobsage.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Job> postJob(@RequestBody JobRequest request) {
        // Get admin ID from security context
        Long adminId = 1L; // Replace with actual admin ID from security context
        Job job = adminService.postJob(adminId, request);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Job>> getAdminJobs() {
        // Get admin ID from security context
        Long adminId = 1L; // Replace with actual admin ID from security context
        List<Job> jobs = adminService.getAdminJobs(adminId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobs/{jobId}/applications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobApplication>> getJobApplications(@PathVariable Long jobId) {
        List<JobApplication> applications = adminService.getJobApplications(jobId);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/applications/{applicationId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status) {
        adminService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok().build();
    }
}