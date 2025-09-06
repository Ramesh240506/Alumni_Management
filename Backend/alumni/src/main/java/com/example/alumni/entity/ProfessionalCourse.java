package com.example.alumni.entity;

import com.example.alumni.entity.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a professional course or certification completed by an alumnus.
 * This is a verifiable record that must be approved by a Course Provider and the College.
 */
@Entity
@Table(name = "Professional_Courses")
@Getter
@Setter
public class ProfessionalCourse {

    @Id
    @Column(name = "courseRecordId", length = 36, nullable = false, updatable = false)
    private String courseRecordId;

    @Column(name = "courseName", length = 255, nullable = false)
    private String courseName;

    @Column(name = "completion_date", nullable = false)
    private LocalDate completionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // --- Relationships ---

    /**
     * Many-to-One relationship with AlumniProfile.
     * Many professional course records can belong to a single alumnus.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumniUserId", referencedColumnName = "alumniUserId", nullable = false)
    private AlumniProfile alumniProfile;

    /**
     * Many-to-One relationship with CourseProvider.
     * Many professional course records can be issued by a single provider.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "providerId", referencedColumnName = "providerId", nullable = false)
    private CourseProvider courseProvider;


    @PrePersist
    public void prePersist() {
        if (this.courseRecordId == null) {
            this.courseRecordId = UUID.randomUUID().toString();
        }
    }
}