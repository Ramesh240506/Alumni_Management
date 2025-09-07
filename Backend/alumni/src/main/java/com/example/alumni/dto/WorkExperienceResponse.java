package com.example.alumni.dto;

import com.example.alumni.entity.enums.RecordStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class WorkExperienceResponse {
    private String experienceId;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private RecordStatus status;
    private AlumnusInfo alumnus;
    
    @Data
    @Builder
    public static class AlumnusInfo {
        private String userId;
        private String name;
    }
}