package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class EventResponse {
    private String eventId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private boolean isVirtual;
    private CollegeInfo college;

    @Data
    @Builder
    public static class CollegeInfo {
        private String collegeId;
        private String name;
    }
}