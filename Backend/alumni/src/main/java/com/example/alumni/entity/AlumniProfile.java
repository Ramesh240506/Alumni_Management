package com.example.alumni.entity;

import com.example.alumni.entity.enums.VerificationStatus; // <-- Make sure to add this import
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * Represents the detailed profile for a user with the ALUMNI role.
 * This entity has a one-to-one relationship with the User entity, sharing the same primary key.
 */
@Entity
@Table(name = "Alumni_Profiles")
@Getter
@Setter
@ToString(exclude = {"user", "college", "workExperiences", "professionalCourses", "academicRecords", "digitalId", "eventRsvps"})
public class AlumniProfile {

    @Id
    @Column(name = "alumniUserId", length = 36)
    private String alumniUserId;

    @Column(name = "firstName", length = 100, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 100, nullable = false)
    private String lastName;

    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    // --- FIELD TO ADD BACK ---
    /**
     * Tracks the verification status of the alumni profile, managed by a college administrator.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "verificationStatus", length = 30, nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING_APPROVAL;
    // -------------------------

    @Column(name = "privacySettings", columnDefinition = "JSON")
    private String privacySettings;


    // --- Relationships ---
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "alumniUserId")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeId", nullable = false)
    private College college;
    
    // --- Inverse Relationships (for navigation) ---

    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<WorkExperience> workExperiences;
    
    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProfessionalCourse> professionalCourses;

    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AcademicRecord> academicRecords;

    @OneToOne(mappedBy = "alumniProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DigitalId digitalId;

    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<EventRsvp> eventRsvps;
}