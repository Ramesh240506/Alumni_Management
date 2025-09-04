package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents the secure digital ID for an alumnus.
 * This entity has a strict one-to-one relationship with an AlumniProfile.
 */
@Entity
@Table(name = "Digital_IDs")
@Getter
@Setter
@NoArgsConstructor // Good practice to have a no-args constructor for JPA entities
public class DigitalId {

    /**
     * The primary key. This is a secure, unique token (e.g., a JWT or a random string)
     * that will be encoded into the QR code. It is generated and set by the service layer.
     */
    @Id
    @Column(name = "idToken", length = 255, nullable = false, updatable = false)
    private String idToken;

    /**
     * A flag to quickly enable or disable the validity of this ID without deleting it.
     */
    @Column(name = "isValid", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isValid = true;

    /**
     * The timestamp when this digital ID was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * An optional expiration timestamp for the ID, enhancing security.
     */
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    // --- Relationship ---

    /**
     * A one-to-one relationship with the AlumniProfile.
     * Each DigitalId is uniquely associated with one Alumnus.
     * This is the "owning" side of the relationship as it holds the foreign key.
     * The `unique = true` on the JoinColumn is crucial for enforcing the one-to-one constraint
     * at the database level.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumniUserId", referencedColumnName = "alumniUserId", nullable = false, unique = true)
    private AlumniProfile alumniProfile;

}