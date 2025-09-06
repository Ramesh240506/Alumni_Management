package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class WorkExperienceResponse {
    private UUID recordId;
    private UUID alumniUserId;
    private String alumniName;
    private String companyName;
    private String jobTitle;
    private String status;
}
