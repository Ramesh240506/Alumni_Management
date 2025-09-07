package com.example.alumni.dto;

import com.example.alumni.entity.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequest {

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    private String location;

    @NotNull(message = "Job type is required")
    private JobType type;
    
    // The companyId will be associated with the employer who posts it.
    // If it's a referral, the service will get the company from the alumnus's profile.
}