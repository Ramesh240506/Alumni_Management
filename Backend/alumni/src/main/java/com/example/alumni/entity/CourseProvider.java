package com.example.alumni.entity;

import com.example.alumni.entity.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an institution that provides professional courses or certifications.
 * These providers must be verified by the platform admin.
 */
@Entity
@Table(name = "Course_Providers")
@Getter
@Setter
public class CourseProvider {

    @Id
    @Column(name = "providerId", length = 36, nullable = false, updatable = false)
    private String providerId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "website", length = 255)
    private String website;

    @Enumerated(EnumType.STRING)
    @Column(name = "verificationStatus", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING_APPROVAL;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- Relationships ---

    /**
     * A course provider can issue many professional course completion records.
     * This defines the "one" side of the One-to-Many relationship with ProfessionalCourse.
     * Deleting a CourseProvider should NOT cascade and delete verified credentials. This
     * must be handled by business logic in the service layer (e.g., prevent deletion
     * if credentials exist).
     */
    @OneToMany(
        mappedBy = "courseProvider", // Refers to the 'courseProvider' field in ProfessionalCourse
        fetch = FetchType.LAZY
    )
    private Set<ProfessionalCourse> providedCourses;


    @PrePersist
    public void prePersist() {
        if (this.providerId == null) {
            this.providerId = UUID.randomUUID().toString();
        }
    }
}