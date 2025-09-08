package com.example.alumni.serviceimpl;

import org.springframework.stereotype.Service;

import com.example.alumni.service.AcademicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcademicServiceImpl implements AcademicService {
    // This service can be used for future academic-specific features.
    // For example, an admin might use this service to get a list of all
    // available 'degreeName' options to ensure data consistency.
    // For now, its functions are handled within other services like CollegeServiceImpl.
}