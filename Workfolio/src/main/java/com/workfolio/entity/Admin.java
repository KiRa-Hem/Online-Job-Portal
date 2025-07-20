package com.workfolio.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.workfolio.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "admins")
public class Admin {
    @Id
    private String companyId;
    private String password;
    private String name;

    public AdminDTO toDTO() {
        return new AdminDTO(this.companyId, this.password, this.name);
    }
}