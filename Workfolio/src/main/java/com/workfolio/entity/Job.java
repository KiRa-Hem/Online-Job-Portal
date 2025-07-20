package com.workfolio.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.workfolio.dto.JobDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jobs")
public class Job {
    @Id
    private String jobId;
    private String title;
    private String description;
    private String url; // For external jobs

    public JobDTO toDTO() {
        return new JobDTO(this.jobId, this.title, this.description, this.url);
    }
}