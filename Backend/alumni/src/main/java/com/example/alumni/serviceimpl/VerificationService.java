package com.example.alumni.serviceimpl;

import com.example.alumni.dto.PendingRecordsResponse;
import java.util.UUID;

public interface VerificationService {
    PendingRecordsResponse getPendingCollegeApprovals();
    void approveRecordByCollege(UUID recordId);
}