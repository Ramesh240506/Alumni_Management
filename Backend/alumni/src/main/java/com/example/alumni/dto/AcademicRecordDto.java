package com.example.alumni.dto;

import com.example.alumni.entity.enums.RecordStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcademicRecordDto {
    private String recordId;
    private String degreeName;
    private String major;
    private Integer graduationYear;
    private RecordStatus status;
    private UserInfo alumnus;

    @Data
    @Builder
    public static class UserInfo {
        private String userId;
        private String name;
    }
}