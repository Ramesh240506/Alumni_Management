package com.example.alumni.entity.enums;

public enum RecordStatus {
    PENDING_AUTHORITY,  // Awaiting verification from the employer
    PENDING_COLLEGE,    // Awaiting final approval from the college
    VERIFIED,           // Fully verified and locked
    REJECTED,           // The request was rejected by an authority
    DISPUTED            // A verified record has been flagged for review
}