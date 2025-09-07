package com.example.alumni.dto;

import com.example.alumni.entity.enums.JobType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JobResponse {
    private String jobId;
    private String title;
    private String description;
    private String location;
    private JobType type;
    private boolean isReferral;
    private LocalDateTime createdAt;
    private CompanyInfo company;
    private UserInfo postedBy;

    // Nested DTO for basic company info
    @Data
    @Builder
    public static class CompanyInfo {
        private String companyId;
        private String name;
    }

    // Nested DTO for basic user info
    @Data
    @Builder
    public static class UserInfo {
        private String userId;
        private String name; // Combination of firstName and lastName
    }
}