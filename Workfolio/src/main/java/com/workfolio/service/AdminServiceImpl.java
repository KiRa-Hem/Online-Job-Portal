package com.workfolio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.workfolio.dto.AdminDTO;
import com.workfolio.dto.QuestionDTO;
import com.workfolio.entity.Admin;
import com.workfolio.entity.Question;
import com.workfolio.exception.JobPortalException;
import com.workfolio.repository.AdminRepository;
import com.workfolio.repository.QuestionRepository;
import com.workfolio.utility.Utilities;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service(value = "adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For secure password storage

    @Override
    public AdminDTO loginAdmin(AdminDTO adminDTO) throws JobPortalException {
        Admin admin = adminRepository.findByCompanyId(adminDTO.getCompanyId())
                .orElseThrow(() -> new JobPortalException("Admin not found"));
        if (!passwordEncoder.matches(adminDTO.getPassword(), admin.getPassword())) {
            throw new JobPortalException("Invalid credentials");
        }
        adminDTO.setName(admin.getName()); // Return additional admin details
        return adminDTO;
    }

    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) throws JobPortalException {
        adminRepository.findByCompanyId(questionDTO.getCompanyId())
                .orElseThrow(() -> new JobPortalException("Unauthorized company"));

        Question question = new Question();
        question.setQuestionId(Utilities.getNextSequence("questions"));
        question.setText(questionDTO.getText());
        question.setType(questionDTO.getType());
        question.setOptions(questionDTO.getOptions());
        question.setAnswer(questionDTO.getAnswer());
        question.setCompanyId(questionDTO.getCompanyId());
        question = questionRepository.save(question);
        return question.toDTO();
    }

    @Override
    public QuestionDTO updateQuestion(QuestionDTO questionDTO) throws JobPortalException {
        Question existingQuestion = questionRepository.findById(questionDTO.getQuestionId())
                .orElseThrow(() -> new JobPortalException("Question not found"));
        if (!existingQuestion.getCompanyId().equals(questionDTO.getCompanyId())) {
            throw new JobPortalException("Unauthorized to update this question");
        }

        existingQuestion.setText(questionDTO.getText());
        existingQuestion.setType(questionDTO.getType());
        existingQuestion.setOptions(questionDTO.getOptions());
        existingQuestion.setAnswer(questionDTO.getAnswer());
        questionRepository.save(existingQuestion);
        return existingQuestion.toDTO();
    }

    @Override
    public List<QuestionDTO> getQuestions() throws JobPortalException {
        // Get authenticated companyId from security context
        String companyId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
        return questionRepository.findByCompanyId(companyId).stream()
                .map(Question::toDTO)
                .toList();
    }
}