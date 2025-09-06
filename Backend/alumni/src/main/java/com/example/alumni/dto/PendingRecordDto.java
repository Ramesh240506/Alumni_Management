package com.example.alumni.dto;

import com.example.alumni.entity.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingRecordDto {
    private String recordId;
    private String alumniUserId;
    private String recordDetails; // e.g., "Software Engineer at Google" or "B.Sc. in Computer Science"
    private String type; // "Work" or "Academic"
    private RecordStatus status;
}