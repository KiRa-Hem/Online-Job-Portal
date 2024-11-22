package com.jobsage.dto;

import lombok.Data;

@Data
public class JobRequest {
    private String title;
    private String description;
    private String requirements;
    private String benefits;
    private String location;
    private String type;
    private String salary;
}