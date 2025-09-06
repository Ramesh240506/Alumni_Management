package com.example.alumni.dto;

import lombok.Data;

@Data
public class ApprovalRequest {
    private boolean approved;
    private String reason; // Optional: for providing a reason for rejection
}