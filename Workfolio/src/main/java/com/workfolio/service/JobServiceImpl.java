package com.workfolio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workfolio.dto.ApplicantDTO;
import com.workfolio.dto.JobDTO;
import com.workfolio.dto.ResumeDTO;
import com.workfolio.entity.Applicant;
import com.workfolio.exception.JobPortalException;
import com.workfolio.repository.ApplicantRepository;
import com.workfolio.repository.JobRepository;
import com.workfolio.service.UserService;

@Service(value="jobService")
public class JobServiceImpl implements JobService {
    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService; // Inject UserService for resume generation

    @Override
    public ApplicantDTO applyJob(ApplicantDTO applicantDTO) throws JobPortalException {
        Applicant applicant = applicantDTO.toEntity();
        applicantRepository.save(applicant);
        return applicant.toDTO();
    }

    @Override
    public List<JobDTO> getAvailableJobs() throws JobPortalException {
        // Fetch all jobs from the repository
        return jobRepository.findAll().stream()
                .map(Job::toDTO)
                .toList();
    }

    @Override
    public ApplicantDTO autoApplyToJob(String jobId, ApplicantDTO applicantDTO) throws JobPortalException {
        // Fetch job details from repository
        JobDTO job = jobRepository.findById(jobId)
                .map(Job::toDTO)
                .orElseThrow(() -> new JobPortalException("Job not found"));

        // Generate ATS-compatible resume
        ResumeDTO resumeDTO = userService.generateResume(applicantDTO.toUserDTO());
        applicantDTO.setResumeContent(resumeDTO.getRawContent()); // Store resume in applicant data

        // Save application
        Applicant applicant = applicantDTO.toEntity();
        applicantRepository.save(applicant);
        return applicant.toDTO();
    }

    @Override
    public List<ApplicantDTO> applyToExternalJobs(List<String> jobUrls, ApplicantDTO applicantDTO) throws JobPortalException {
        List<ApplicantDTO> applications = new ArrayList<>();
        ResumeDTO resumeDTO = userService.generateResume(applicantDTO.toUserDTO());
        applicantDTO.setResumeContent(resumeDTO.getRawContent());

        // Use UserService to auto-apply to external jobs
        userService.autoApplyJobs(applicantDTO.toUserDTO(), jobUrls, resumeDTO);

        // Save each application
        Applicant applicant = applicantDTO.toEntity();
        applicantRepository.save(applicant);
        applications.add(applicant.toDTO());

        return applications;
    }
}