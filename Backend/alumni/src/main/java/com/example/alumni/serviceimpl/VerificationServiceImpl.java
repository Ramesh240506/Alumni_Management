package com.example.alumni.serviceimpl;

import com.example.alumni.entity.College;
import com.example.alumni.entity.enums.VerificationStatus;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.CollegeRepository;
import com.example.alumni.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    
    private final CollegeRepository collegeRepository;
    // Inject CompanyRepository, CourseProviderRepository as needed

    @Override
    @Transactional
    public void approveCollege(String collegeId) {
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new NotFoundException("College not found with ID: " + collegeId));
        college.setVerificationStatus(VerificationStatus.VERIFIED);
        collegeRepository.save(college);
    }

    @Override
    @Transactional
    public void rejectCollege(String collegeId) {
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new NotFoundException("College not found with ID: " + collegeId));
        college.setVerificationStatus(VerificationStatus.REJECTED);
        collegeRepository.save(college);
    }
    
    // Implement approve/reject for Company and CourseProvider similarly...
    @Override
    public void approveCompany(String companyId) {}

    @Override
    public void rejectCompany(String companyId) {}
}