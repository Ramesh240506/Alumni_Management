package com.example.alumni.repository;

import com.example.alumni.entity.CourseProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseProviderRepository extends JpaRepository<CourseProvider, String> {
}