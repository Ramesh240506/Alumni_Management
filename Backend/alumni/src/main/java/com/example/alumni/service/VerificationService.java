package com.example.alumni.service;

public interface VerificationService {
    void approveCollege(String collegeId);
    void rejectCollege(String collegeId);
    void approveCompany(String companyId);
    void rejectCompany(String companyId);
}