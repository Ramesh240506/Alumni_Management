package com.example.alumni.repository;

import com.example.alumni.entity.AcademicRecord;
import com.example.alumni.entity.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, String> {
    List<AcademicRecord> findByStatus(RecordStatus status);
}