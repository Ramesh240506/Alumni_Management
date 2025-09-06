package com.example.alumni.dto;

import com.example.alumni.entity.enums.RsvpStatus;
import lombok.Data;

@Data
public class RsvpRequest {
    private RsvpStatus status;
}