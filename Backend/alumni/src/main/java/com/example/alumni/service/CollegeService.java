package com.example.alumni.service;

import com.example.alumni.dto.PendingAlumniDto;
import com.example.alumni.dto.PendingRecordDto;
import java.util.List;

public interface CollegeService {
    List<PendingAlumniDto> getPendingAlumniRegistrations();
    void approveAlumni(String alumniId);
    List<PendingRecordDto> getPendingRecords();
    void approveRecord(String recordId, boolean isApproved);
}