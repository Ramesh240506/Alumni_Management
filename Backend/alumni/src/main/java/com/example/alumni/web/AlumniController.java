package com.example.alumni.web;

import com.example.alumni.dto.AlumniNetworkResponse;
import com.example.alumni.dto.AlumniProfileRequest;
import com.example.alumni.dto.AlumniProfileResponse;
import com.example.alumni.dto.DigitalIdResponse;
import com.example.alumni.dto.DonationResponse;
import com.example.alumni.service.AlumniService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumni")
@RequiredArgsConstructor
public class AlumniController {

    private final AlumniService alumniService;

    @GetMapping("/me")
    public ResponseEntity<AlumniProfileResponse> viewOwnProfile() {
        return ResponseEntity.ok(alumniService.getCurrentAlumniProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<AlumniProfileResponse> updateOwnProfile(@Valid @RequestBody AlumniProfileRequest request) {
        return ResponseEntity.ok(alumniService.updateCurrentAlumniProfile(request));
    }

    /**
     * NOTE: The endpoint is /digital-id, not /digital-id/{id}.
     * The ID is implicitly the ID of the authenticated user, which is more secure.
     */
    @GetMapping("/digital-id")
    public ResponseEntity<DigitalIdResponse> getDigitalId() {
        return ResponseEntity.ok(alumniService.getDigitalIdForCurrentUser());
    }

    @GetMapping("/donation-history")
    public ResponseEntity<List<DonationResponse>> getDonationHistory() {
        return ResponseEntity.ok(alumniService.getDonationHistoryForCurrentUser());
    }
    // ... inside AlumniController ...

// --- ADD THIS NEW ENDPOINT ---
@GetMapping("/network")
public ResponseEntity<List<AlumniNetworkResponse>> getAlumniNetwork() {
    return ResponseEntity.ok(alumniService.getAlumniNetwork());
}
}