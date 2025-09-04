package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "academic_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AcademicRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumni_user_id", nullable = false)
    @ToString.Exclude // Exclude to prevent potential StackOverflowError
    private AlumniProfile alumniUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    @ToString.Exclude // Exclude to prevent potential StackOverflowError
    private College college;

    @Column(nullable = false)
    private String degreeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordStatus status; // ENUM: PENDING, VERIFIED, REJECTED

    // Enum for Record Status
    public enum RecordStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }
}