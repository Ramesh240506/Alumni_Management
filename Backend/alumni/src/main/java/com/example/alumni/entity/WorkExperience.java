package com.example.alumni.entity;

import com.example.alumni.entity.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Work_Experiences") // Matches the table name in your schema
@Getter
@Setter
public class WorkExperience {

    @Id
    @Column(name = "experienceId", length = 36, nullable = false, updatable = false)
    private String experienceId; // Using String for UUID as per your schema

    @Column(name = "jobTitle", length = 255, nullable = false)
    private String jobTitle;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate") // Nullable for current jobs
    private LocalDate endDate;

    @Enumerated(EnumType.STRING) 
    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumniUserId", referencedColumnName = "alumniUserId", nullable = false)
    private AlumniProfile alumniProfile;

    /**
     * Many-to-One relationship with Company.
     * Many work experiences (from different alumni) can be associated with one company.
     * FetchType.LAZY is used for performance optimization.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", referencedColumnName = "companyId", nullable = false)
    private Company company;

    // It's good practice to have a pre-persist method for setting the UUID
    @PrePersist
    public void prePersist() {
        if (this.experienceId == null) {
            this.experienceId = java.util.UUID.randomUUID().toString();
        }
    }
}