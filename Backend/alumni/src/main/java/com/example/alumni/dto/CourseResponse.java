package com.example.alumni.dto;

import com.example.alumni.entity.enums.RecordStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CourseResponse {
    private String courseRecordId;
    private String courseName;
    private LocalDate completionDate;
    private RecordStatus status;
    private ProviderInfo provider;

    @Data
    @Builder
    public static class ProviderInfo {
        private String providerId;
        private String name;
    }
}