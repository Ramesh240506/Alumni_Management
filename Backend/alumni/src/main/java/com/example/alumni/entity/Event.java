package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an event organized by a college for its alumni.
 * This can be a physical meetup, a webinar, or any other gathering.
 */
@Entity
@Table(name = "Events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class Event {

    @Id
    @Column(name = "eventId", length = 36, nullable = false, updatable = false)
    private String eventId;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "is_virtual", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVirtual = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- Relationships ---
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeId", nullable = false)
    private College college;
    
    @OneToMany(
        mappedBy = "event",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<EventRsvp> rsvps;

    @PrePersist
    public void prePersist() {
        if (this.eventId == null) {
            this.eventId = UUID.randomUUID().toString();
        }
    }

    // The conflicting builder() method has been REMOVED.
}