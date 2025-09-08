package com.example.alumni.entity;

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
@Table(name = "alumni_profiles") // Aligns with JPA's snake_case naming strategy
@Getter
@Setter
@ToString(exclude = {"user", "college", "workExperiences", "professionalCourses", "academicRecords", "digitalId", "eventRsvps"})
public class AlumniProfile {

    @Id
    @Column(name = "alumni_user_id", length = 36)
    private String alumniUserId;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(length = 20, unique = true)
    private String phone;
    
    @Column(name = "current_city", length = 100)
    private String currentCity;
    
    @Column(name = "current_country", length = 100)
    private String currentCountry;
    
    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "privacy_settings", columnDefinition = "JSON")
    private String privacySettings;

    // --- Relationships ---

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "alumni_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

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