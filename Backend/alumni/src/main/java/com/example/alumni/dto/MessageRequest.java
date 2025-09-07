package com.example.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank
    private String receiverId;
    @NotBlank
    private String content;
}