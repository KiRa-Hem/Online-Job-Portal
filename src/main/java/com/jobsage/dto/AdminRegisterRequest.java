package com.jobsage.dto;

import lombok.Data;

@Data
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String companyName;
}