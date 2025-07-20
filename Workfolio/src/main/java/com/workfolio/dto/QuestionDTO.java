package com.workfolio.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private String questionId;
    private String text; // Question text
    private String type; // "MCQ" or "CODE"
    private List<String> options; // For MCQ, list of choices
    private String answer; // Correct answer or code solution
    private String companyId; // Links to the admin company
}