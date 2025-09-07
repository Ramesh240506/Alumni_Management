package com.example.alumni.service;

import com.example.alumni.dto.AcademicRecordDto;
import java.util.List;

public interface CollegeService {
    List<AcademicRecordDto> getPendingVerifications();
    void approveRecord(String recordId);
    void rejectRecord(String recordId);
}