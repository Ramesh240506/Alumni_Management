package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
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

    /**
     * The College that is hosting this event.
     * Every event must be associated with one college.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeId", nullable = false)
    private College college;

    /**
     * The set of all RSVPs made for this event.
     * This defines the "one" side of the One-to-Many relationship with EventRsvp.
     * CascadeType.ALL is used because RSVPs are meaningless without the event. If the event
     * is deleted, all its RSVPs should be deleted too.
     */
    @OneToMany(
        mappedBy = "event", // Refers to the 'event' field in the EventRsvp entity
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
}