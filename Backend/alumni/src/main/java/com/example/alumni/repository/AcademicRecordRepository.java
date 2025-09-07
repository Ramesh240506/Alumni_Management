package com.example.alumni.repository;

import com.example.alumni.entity.AcademicRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, String> {
}