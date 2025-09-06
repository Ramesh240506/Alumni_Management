package com.example.alumni.entity;

import com.example.alumni.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a user account in the system. This is the core entity for authentication
 * and serves as the base for all roles (Alumni, Admin, etc.).
 */
@Entity
@Table(name = "Users", uniqueConstraints = { // It's good practice to name the table "Users" to avoid SQL keyword conflicts
    @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id", length = 36, nullable = false, updatable = false)
    private String userId;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    // --- Relationships ---

    /**
     * One-to-One relationship with AlumniProfile. A User can be an Alumnus.
     * This is the "inverse" side of the relationship, as the foreign key is in the AlumniProfile table.
     * 'mappedBy = "user"' refers to the 'user' field in the AlumniProfile entity.
     * CascadeType.ALL means if we delete a User, their associated AlumniProfile is also deleted.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private AlumniProfile alumniProfile;

    /**
     * One-to-Many relationship with UserAffiliation.
     * A user (e.g., a College Admin) can be affiliated with an entity.
     * If a user is deleted, their affiliations are no longer valid and should be removed.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserAffiliation> affiliations;

    /**
     * One-to-Many relationship with Audit_Log.
     * We DO NOT cascade deletes here. If a user is deleted, their audit history should be preserved
     * for security and accountability. The foreign key in the database should be configured to SET NULL.
     */
    @OneToMany(mappedBy = "actionByUser", fetch = FetchType.LAZY)
    private Set<AuditLog> auditLogs;

    /**
     * A user can be the sender in many messages.
     * No cascade on delete; messages should be preserved.
     */
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<Message> sentMessages;

    /**
     * A user can be the receiver of many messages.
     * No cascade on delete; messages should be preserved.
     */
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private Set<Message> receivedMessages;


    @PrePersist
    public void prePersist() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString();
        }
    }
}