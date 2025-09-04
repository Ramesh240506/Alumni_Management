package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false)
    private UUID targetRecordId; // The ID of the record being audited

    @Column(nullable = false)
    private String recordType; // e.g., "AlumniProfile", "AcademicRecord", "WorkExperience"

    @Column(nullable = false)
    private String action; // e.g., "CREATE", "UPDATE", "DELETE", "VERIFY"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_by_user_id", nullable = false)
    @ToString.Exclude // Exclude to prevent potential StackOverflowError with bi-directional relationships
    private User actionByUser;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}