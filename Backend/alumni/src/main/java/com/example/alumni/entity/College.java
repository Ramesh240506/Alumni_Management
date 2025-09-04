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
@Table(name = "Colleges")
@Getter
@Setter
public class College {

    @Id
    @Column(name = "collegeId", length = 36, nullable = false, updatable = false)
    private String collegeId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "website", length = 255)
    private String website;

    @Enumerated(EnumType.STRING)
    @Column(name = "verificationStatus", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING_APPROVAL;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private Set<AlumniProfile> alumniProfiles;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private Set<AcademicRecord> academicRecords;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Event> events;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<FundraisingCampaign> fundraisingCampaigns;

    @PrePersist
    public void prePersist() {
        if (this.collegeId == null) {
            this.collegeId = UUID.randomUUID().toString();
        }
    }
}