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
@Table(name = "Alumni_Profiles") // Matching the schema name convention
@Getter
@Setter
@ToString(exclude = {"user", "college", "workExperiences", "professionalCourses", "academicRecords", "digitalId", "eventRsvps"}) // Exclude lazy fields from ToString to prevent errors
public class AlumniProfile {

    @Id
    @Column(name = "alumniUserId", length = 36) // CORRECTED: Was "alumniUserld"
    private String alumniUserId;

    @Column(name = "firstName", length = 100, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 100, nullable = false)
    private String lastName;

    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    @Column(name = "privacySettings", columnDefinition = "JSON")
    private String privacySettings;

    // --- Relationships ---

    /**
     * One-to-One with User, sharing the primary key.
     * @MapsId tells JPA that this entity's ID comes from this relationship.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "alumniUserId") // CORRECTED: Was "alumniUserld"
    private User user;

    /**
     * The College to which this alumnus belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeId", nullable = false) // CORRECTED: Was "collegeld"
    private College college;
    
    // --- Inverse Relationships (for navigation) ---

    // Using Set instead of List is a best practice for "to-many" relationships
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