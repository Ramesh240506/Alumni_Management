package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents structured feedback provided by an employer for an intern.
 * This entity captures performance metrics and comments in a structured format.
 */
@Entity
@Table(name = "Internship_Feedback")
@Getter
@Setter
public class InternshipFeedback {

    @Id
    @Column(name = "feedbackId", length = 36, nullable = false, updatable = false)
    private String feedbackId;

    /**
     * Stores the structured feedback content as a JSON string.
     * Example: {"technical_skills": 5, "communication": 4, "comments": "Great work..."}
     * The application layer (service/DTO) will be responsible for serializing/deserializing this.
     */
    @Column(name = "feedbackContent", nullable = false, columnDefinition = "JSON")
    private String feedbackContent;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- Relationships ---

    /**
     * The User (who must be an Alumnus/Student) who received the internship feedback.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internUserId", nullable = false)
    private User internUser;

    /**
     * The Company where the internship took place.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;

    /**
     * The specific User (who must be an Employer Representative) who submitted this feedback.
     * This provides accountability for the feedback record.
     * The DB foreign key should be ON DELETE SET NULL.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_user_id") // As per the full MySQL schema
    private User reviewer;


    @PrePersist
    public void prePersist() {
        if (this.feedbackId == null) {
            this.feedbackId = UUID.randomUUID().toString();
        }
    }
}