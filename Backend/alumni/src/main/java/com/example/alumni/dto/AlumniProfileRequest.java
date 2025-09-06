package com.example.alumni.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Map;

@Data
public class AlumniProfileRequest {

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phone;

    // Using Map allows for flexible JSON like {"show_email": "connections", "show_phone": "none"}
    private Map<String, String> privacySettings;
}