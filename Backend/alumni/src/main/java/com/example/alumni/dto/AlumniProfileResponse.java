package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlumniProfileResponse {
    
    private String alumniUserId;
    private String email; // From the User entity
    private String firstName;
    private String lastName;
    private String phone;
    private Object privacySettings; // Using Object for flexibility with JSON
    private CollegeDto college; // Nested DTO for college info

    // A nested DTO to avoid exposing the full College entity
    @Data
    @Builder
    public static class CollegeDto {
        private String collegeId;
        private String name;
    }
}