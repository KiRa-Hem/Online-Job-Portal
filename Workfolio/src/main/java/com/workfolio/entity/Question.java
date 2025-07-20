package com.workfolio.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.workfolio.dto.QuestionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question {
    @Id
    private String questionId;
    private String text;
    private String type;
    private List<String> options;
    private String answer;
    private String companyId;

    public QuestionDTO toDTO() {
        return new QuestionDTO(this.questionId, this.text, this.type, this.options, this.answer, this.companyId);
    }
}