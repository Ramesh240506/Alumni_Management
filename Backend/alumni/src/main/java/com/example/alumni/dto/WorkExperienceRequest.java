package com.example.alumni.dto;

import lombok.Data;

@Data
public class WorkExperienceRequest {
    // FIX: Changed from UUID to String
    private String alumniUserId; 

    // FIX: Changed from UUID to String
    private String companyId;    
    
    private String jobTitle;
}
