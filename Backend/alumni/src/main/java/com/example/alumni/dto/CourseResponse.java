package com.example.alumni.dto;

import com.example.alumni.entity.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for returning details of a professional course record after an API operation
 * (e.g., creation or verification).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private UUID courseRecordId;

    private String courseName;

    private String alumniName;

    private String providerName;

    private RecordStatus status;
}