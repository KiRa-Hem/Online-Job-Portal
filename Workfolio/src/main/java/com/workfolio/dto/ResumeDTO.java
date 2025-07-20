package com.workfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private String name;
    private String contact; // Email, phone, etc.
    private String summary; // Professional summary
    private String skills; // Comma-separated skills
    private String experience; // Work experience details
    private String education; // Educational background
    private String rawContent; // Full AI-generated resume text
}