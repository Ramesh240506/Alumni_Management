package com.example.alumni.entity;

import com.example.alumni.entity.enums.VerificationStatus1;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "college")
@Data
public class College {

    @Id
    @Column(name = "collegeld", nullable = false, length = 36)
    private String collegeId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "verificationStatus", nullable = false)
    private VerificationStatus1 verificationStatus;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private List<AlumniProfile> alumniProfiles;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private List<AcademicRecord> academicRecords;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "college", fetch = FetchType.LAZY)
    private List<FundraisingCampaign> fundraisingCampaigns;
}