package com.example.alumni.entity;

import com.example.alumni.entity.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Academic_Records")
@Getter
@Setter
public class AcademicRecord {

    @Id
    @Column(name = "recordId", length = 36, nullable = false, updatable = false)
    private String recordId;

    @Column(name = "degreeName", length = 255, nullable = false)
    private String degreeName;

    @Column(name = "major", length = 255)
    private String major;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumniUserId", nullable = false)
    private AlumniProfile alumniProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeId", nullable = false)
    private College college;

    @PrePersist
    public void prePersist() {
        if (this.recordId == null) {
            this.recordId = UUID.randomUUID().toString();
        }
    }
}