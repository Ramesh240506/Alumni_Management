package com.example.alumni.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "alumni_profile")
@Data
public class AlumniProfile {

    @Id
    @Column(name = "alumniUserld", length = 36)
    private String alumniUserId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "alumniUserld")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeld", nullable = false)
    private College college;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "JSON")
    private String privacySettings;

    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicRecord> academicRecords;

    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "alumniProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessionalCourse> professionalCourses;

    @OneToOne(mappedBy = "alumniProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DigitalId digitalId;
}