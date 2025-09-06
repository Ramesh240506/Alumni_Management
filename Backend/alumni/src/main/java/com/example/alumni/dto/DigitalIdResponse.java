package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class DigitalIdResponse {
    private String idToken;
    private boolean isValid;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}