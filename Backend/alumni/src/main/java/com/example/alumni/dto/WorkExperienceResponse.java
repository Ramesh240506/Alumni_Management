package com.example.alumni.dto;

import com.example.alumni.entity.enums.RecordStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkExperienceResponse {
    // FIX: Changed from UUID to String
    private String experienceId; 

    private String alumniName;
    private String companyName;
    private String jobTitle;
    private RecordStatus status;
}