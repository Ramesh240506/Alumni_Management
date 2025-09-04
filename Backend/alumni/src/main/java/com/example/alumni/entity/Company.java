package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.example.alumni.entity.enums.VerificationStatus1;

@Entity
@Table(name = "company")
@Data
public class Company {

    @Id
    @Column(name = "companyld", nullable = false, length = 36)
    private String companyId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "verificationStatus", nullable = false)
    private VerificationStatus1 verificationStatus;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<JobPosting> jobPostings;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<InternshipFeedback> internshipFeedbacks;
}