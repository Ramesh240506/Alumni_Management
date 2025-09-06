package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class MessageResponse {
    private Long messageId;
    private UUID senderId;
    private UUID receiverId;
    private String content;
    private Instant timestamp;
}
