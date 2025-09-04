package com.example.alumni.entity;

import com.example.alumni.entity.enums.JobStatus;
import com.example.alumni.entity.enums.JobType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a job or internship opportunity posted on the platform.
 * It can be a direct posting from an employer or a referral from an alumnus.
 */
@Entity
@Table(name = "Job_Postings")
@Getter
@Setter
public class JobPosting {

    @Id
    @Column(name = "jobId", length = 36, nullable = false, updatable = false)
    private String jobId;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private JobType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status = JobStatus.OPEN; // Default status is OPEN

    @Column(name = "is_referral", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isReferral = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- Relationships ---

    /**
     * The User who created this job posting. This could be an Employer Representative
     * or an Alumnus making a referral.
     * The database foreign key should be configured with ON DELETE SET NULL.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postedByUserId")
    private User postedByUser;

    /**
     * The Company for which this job is posted.
     * A job posting is intrinsically linked to a company.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private Company company;


    @PrePersist
    public void prePersist() {
        if (this.jobId == null) {
            this.jobId = UUID.randomUUID().toString();
        }
    }
}