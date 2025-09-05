package com.example.alumni.dto;

import com.example.alumni.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A Data Transfer Object for safely exposing User information.
 * It excludes sensitive fields like the password hash.
 */
@Data
@Builder
public class UserResponse {

    private String userId;
    private String email;
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
}