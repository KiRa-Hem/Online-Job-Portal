package com.workfolio.service;

import java.util.List;

import com.workfolio.dto.AdminDTO;
import com.workfolio.dto.QuestionDTO;
import com.workfolio.exception.JobPortalException;

public interface AdminService {
    // Admin login authentication
    public AdminDTO loginAdmin(AdminDTO adminDTO) throws JobPortalException;

    // Create a new test question (MCQ or code)
    public QuestionDTO createQuestion(QuestionDTO questionDTO) throws JobPortalException;

    // Update an existing test question
    public QuestionDTO updateQuestion(QuestionDTO questionDTO) throws JobPortalException;

    // Retrieve all questions for the authenticated company
    public List<QuestionDTO> getQuestions() throws JobPortalException;
}