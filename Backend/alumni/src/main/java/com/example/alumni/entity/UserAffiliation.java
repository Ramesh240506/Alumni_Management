package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing the link between a non-alumni user (like a College Admin or Employer Rep)
 * and their respective institution (College, Company, etc.).
 * This entity uses a composite primary key managed by the UserAffiliationId class.
 */
@Entity
@Table(name = "User_Affiliation")
@IdClass(UserAffiliationId.class) // Specifies the class for the composite key
@Getter
@Setter
public class UserAffiliation {

    @Id
    @Column(name = "userId", length = 36)
    private String userId;

    @Id
    @Column(name = "entityId", length = 36)
    private String entityId; // This ID could refer to a College, Company, or CourseProvider

    // --- Relationships ---

    /**
     * Many-to-One relationship with the User entity.
     * This defines the "user" part of the affiliation link.
     * We mark the JoinColumn as not insertable/updatable because the 'userId' field
     * is already managed as part of the composite primary key (@Id).
     * This mapping is primarily for object-oriented navigation (e.g., affiliation.getUser()).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

}