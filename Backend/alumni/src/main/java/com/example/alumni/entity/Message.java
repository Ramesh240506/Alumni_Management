package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a single one-to-one message sent between two users in the system.
 */
@Entity
@Table(name = "Messages")
@Getter
@Setter
public class Message {

    /**
     * The unique identifier for the message, generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private Long messageId;

    /**
     * The text content of the message.
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * The timestamp when the message was created.
     * Automatically set by Hibernate when the entity is first saved.
     */
    @CreationTimestamp
    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    /**
     * The timestamp when the message was marked as read by the receiver.
     * This will be null until the receiver reads the message.
     */
    @Column(name = "read_at")
    private LocalDateTime readAt;

    // --- Relationships ---

    /**
     * The user who sent the message.
     * This is a lazy-loaded relationship for performance.
     * IMPORTANT: The database foreign key for 'senderId' should be configured with
     * 'ON DELETE SET NULL' to preserve message history if a user is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId")
    private User sender;

    /**
     * The user who received the message.
     * This is a lazy-loaded relationship for performance.
     * IMPORTANT: The database foreign key for 'receiverId' should be configured with
     * 'ON DELETE SET NULL' to preserve message history if a user is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId")
    private User receiver;

}