package com.workfolio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workfolio.dto.ApplicantDTO;
import com.workfolio.dto.AdminDTO;
import com.workfolio.dto.QuestionDTO;
import com.workfolio.exception.JobPortalException;
import com.workfolio.service.JobService;
import com.workfolio.service.AdminService;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/applicantDetails")
public class JobAPI {
    @Autowired
    private JobService jobService;

    @Autowired
    private AdminService adminService; // Inject AdminService for test management

    @PostMapping("/applyjob")
    public ResponseEntity<ApplicantDTO> applyJob(@RequestBody ApplicantDTO applicantDTO) throws JobPortalException {
        applicantDTO = jobService.applyJob(applicantDTO);
        return new ResponseEntity<>(applicantDTO, HttpStatus.CREATED);
    }

    // Admin login endpoint
    @PostMapping("/admin/login")
    public ResponseEntity<AdminDTO> adminLogin(@RequestBody AdminDTO adminDTO) throws JobPortalException {
        AdminDTO authenticatedAdmin = adminService.loginAdmin(adminDTO);
        return new ResponseEntity<>(authenticatedAdmin, HttpStatus.OK);
    }

    // Create a new test question (MCQ or code)
    @PostMapping("/admin/questions")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) throws JobPortalException {
        QuestionDTO createdQuestion = adminService.createQuestion(questionDTO);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    // Update an existing test question
    @PostMapping("/admin/questions/{questionId}")
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody QuestionDTO questionDTO) throws JobPortalException {
        QuestionDTO updatedQuestion = adminService.updateQuestion(questionDTO);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    // Retrieve all questions for a company
    @GetMapping("/admin/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestions() throws JobPortalException {
        List<QuestionDTO> questions = adminService.getQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}