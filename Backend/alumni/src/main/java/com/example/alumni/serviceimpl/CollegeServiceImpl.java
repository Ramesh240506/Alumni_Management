package com.example.alumni.serviceimpl;

import com.example.alumni.dto.PendingAlumniDto;
import com.example.alumni.dto.PendingRecordDto;
import com.example.alumni.entity.AcademicRecord;
import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.entity.enums.VerificationStatus;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.AcademicRecordRepository;
import com.example.alumni.repository.AlumniProfileRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final AlumniProfileRepository alumniProfileRepository;
    private final AcademicRecordRepository academicRecordRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public List<PendingAlumniDto> getPendingAlumniRegistrations() {
        return alumniProfileRepository.findByVerificationStatus(VerificationStatus.PENDING_APPROVAL)
                .stream()
                .map(profile -> new PendingAlumniDto(
                        profile.getAlumniUserId(),
                        profile.getFirstName(),
                        profile.getLastName(),
                        profile.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveAlumni(String alumniId) {
        AlumniProfile alumni = alumniProfileRepository.findById(alumniId)
                .orElseThrow(() -> new NotFoundException("Alumni not found with id: " + alumniId));

        alumni.setVerificationStatus(VerificationStatus.VERIFIED);
        alumniProfileRepository.save(alumni);
    }

    @Override
    public List<PendingRecordDto> getPendingRecords() {
        List<PendingRecordDto> workRecords = workExperienceRepository.findByStatus(RecordStatus.PENDING_APPROVAL)
                .stream()
                .map(wr -> new PendingRecordDto(
                        wr.getExperienceId(),
                        wr.getAlumniProfile().getAlumniUserId(),
                        wr.getJobTitle() + " at " + wr.getCompanyName(),
                        "Work",
                        wr.getStatus()))
                .collect(Collectors.toList());
        
        List<PendingRecordDto> academicRecords = academicRecordRepository.findByStatus(RecordStatus.PENDING_APPROVAL)
                .stream()
                .map(ar -> new PendingRecordDto(
                        ar.getRecordId(),
                        ar.getAlumniProfile().getAlumniUserId(),
                        ar.getDegreeName(),
                        "Academic",
                        ar.getStatus()))
                .collect(Collectors.toList());

        return Stream.concat(workRecords.stream(), academicRecords.stream()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveRecord(String recordId, boolean isApproved) {
        boolean recordFound = false;
        
        if (academicRecordRepository.existsById(recordId)) {
            AcademicRecord record = academicRecordRepository.findById(recordId).get();
            record.setStatus(isApproved ? RecordStatus.APPROVED : RecordStatus.REJECTED);
            academicRecordRepository.save(record);
            recordFound = true;
        }

        if (workExperienceRepository.existsById(recordId)) {
            WorkExperience record = workExperienceRepository.findById(recordId).get();
            record.setStatus(isApproved ? RecordStatus.APPROVED : RecordStatus.REJECTED);
            workExperienceRepository.save(record);
            recordFound = true;
        }

        if (!recordFound) {
            throw new NotFoundException("Record not found with id: " + recordId);
        }
    }
}