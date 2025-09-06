package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import java.util.UUID;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming auto-increment for BIGINT
    private Long messageId;

    @Column(nullable = false)
    private UUID sender; // FK to User

    @Column(nullable = false)
    private UUID receiver; // FK to User

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant timestamp; // Added for ordering conversations

   
}
