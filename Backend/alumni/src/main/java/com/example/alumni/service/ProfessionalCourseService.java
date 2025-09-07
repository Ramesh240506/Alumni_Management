package com.example.alumni.service;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;

public interface ProfessionalCourseService {
    CourseResponse requestCourseVerification(CourseRequest request);
}