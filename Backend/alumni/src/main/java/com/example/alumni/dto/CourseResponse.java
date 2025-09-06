package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CourseResponse {
    private UUID recordId;
    private UUID alumniUserId;
    private String alumniName;
    private String providerName;
    private String courseName;
    private String status;
}

