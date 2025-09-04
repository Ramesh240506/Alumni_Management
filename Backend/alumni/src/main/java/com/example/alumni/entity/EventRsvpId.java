package com.example.alumni.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Represents the composite primary key for the EventRsvp entity.
 * This class is necessary because the primary key consists of more than one column.
 * It must be Serializable and implement equals() and hashCode().
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode // Lombok generates the required equals() and hashCode() methods
public class EventRsvpId implements Serializable {

    private String eventId;
    private String alumniUserId;
}