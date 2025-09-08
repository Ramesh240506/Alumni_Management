package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlumniNetworkResponse {
    private String alumniUserId;
    private String firstName;
    private String lastName;
    private String currentCity;
    // In a real app, you'd add lat/lng from a geocoding service or user input
    private Double lat;
    private Double lng;
}