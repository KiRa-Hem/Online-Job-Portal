package com.workfolio.service;

import java.util.List;

import com.workfolio.dto.ApplicantDTO;
import com.workfolio.dto.JobDTO;
import com.workfolio.exception.JobPortalException;

public interface JobService {
    public ApplicantDTO applyJob(ApplicantDTO applicantDTO) throws JobPortalException;

    // Retrieve available jobs (internal and external)
    public List<JobDTO> getAvailableJobs() throws JobPortalException;

    // Auto-apply to jobs using AI-generated resume
    public ApplicantDTO autoApplyToJob(String jobId, ApplicantDTO applicantDTO) throws JobPortalException;

    // Fetch and apply to external jobs
    public List<ApplicantDTO> applyToExternalJobs(List<String> jobUrls, ApplicantDTO applicantDTO) throws JobPortalException;
}