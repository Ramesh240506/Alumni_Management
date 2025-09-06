package com.example.alumni.entity;

import com.example.alumni.entity.enums.RsvpStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents an alumnus's RSVP to an event. This is a junction entity that
 * links an Event to an AlumniProfile in a many-to-many relationship, with an
 * additional attribute (status).
 */
@Entity
@Table(name = "Event_RSVPs") // Matches the pluralized table name in the schema
@IdClass(EventRsvpId.class) // Specifies the class for the composite key
@Getter
@Setter
@Builder
public class EventRsvp {

    @Id
    @Column(name = "eventId", length = 36)
    private String eventId;

    @Id
    @Column(name = "alumniUserId", length = 36)
    private String alumniUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RsvpStatus status;

    @CreationTimestamp
    @Column(name = "rsvp_at", nullable = false, updatable = false)
    private LocalDateTime rsvpAt;

    // --- Relationships ---

    /**
     * The Event to which this RSVP belongs.
     * We mark this as not insertable/updatable because the 'eventId' field is
     * already managed as part of the composite primary key. This is for navigation purposes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", insertable = false, updatable = false)
    private Event event;

    /**
     * The Alumnus who made this RSVP.
     * We mark this as not insertable/updatable because the 'alumniUserId' field is
     * already managed as part of the composite primary key. This is for navigation purposes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumniUserId", insertable = false, updatable = false)
    private AlumniProfile alumniProfile;

}