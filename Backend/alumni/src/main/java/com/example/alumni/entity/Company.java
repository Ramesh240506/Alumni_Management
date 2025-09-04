package com.example.alumni.entity;

import com.example.alumni.entity.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Companies")
@Getter
@Setter
public class Company {

    @Id
    @Column(name = "companyId", length = 36, nullable = false, updatable = false)
    private String companyId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "website", length = 255)
    private String website;

    @Enumerated(EnumType.STRING)
    @Column(name = "verificationStatus", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING_APPROVAL;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<JobPosting> jobPostings;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<InternshipFeedback> internshipFeedbacks;

    @PrePersist
    public void prePersist() {
        if (this.companyId == null) {
            this.companyId = UUID.randomUUID().toString();
        }
    }
}