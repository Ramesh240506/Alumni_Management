package com.example.alumni.service;

public interface AuditService {
    void log(String action, String recordType, String targetRecordId, String details);
}