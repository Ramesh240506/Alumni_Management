package com.example.alumni.serviceimpl;

import com.example.alumni.entity.Company;
import com.example.alumni.exception.ResourceNotFoundException;
import com.example.alumni.repository.CompanyRepository;
import com.example.alumni.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// The "import java.util.UUID;" is no longer needed and has been removed.

/**
 * Implementation of the CompanyService interface.
 * Handles the business logic for company-related operations.
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    // FIX 1: The parameter type is now String, not UUID.
    public Company findCompanyById(String id) { 
        // FIX 2: This now works because the 'id' variable is a String,
        // which is what companyRepository.findById() expects.
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public Company createCompany(Company company) {
        // In a real application, you might add validation to prevent duplicate names
        return companyRepository.save(company);
    }
}