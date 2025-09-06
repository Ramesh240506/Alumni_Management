package com.example.alumni.service;

import com.example.alumni.entity.Company;
import java.util.List;
// The "import java.util.UUID;" is no longer needed and has been removed.

/**
 * Service interface defining business logic for managing Company entities.
 * This would be used by admin-level controllers or other services.
 */
public interface CompanyService {

    /**
     * Finds a company by its unique identifier.
     *
     * @param id The String ID of the company.
     * @return The found Company entity.
     * @throws com.example.alumni.exception.ResourceNotFoundException if no company is found.
     */
    Company findCompanyById(String id); // <-- THIS IS THE FIX: The type is now String.

    /**
     * Retrieves a list of all companies in the system.
     *
     * @return A list of all Company entities.
     */
    List<Company> findAllCompanies();

    /**
     * Creates a new company in the system.
     *
     * @param company The Company entity to create.
     * @return The newly created and saved Company entity.
     */
    Company createCompany(Company company);
}