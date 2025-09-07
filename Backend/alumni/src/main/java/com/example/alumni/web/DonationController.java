package com.example.alumni.web;

import com.example.alumni.dto.DonationRequest;
import com.example.alumni.dto.DonationResponse;
import com.example.alumni.service.DonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationResponse> makeDonation(@Valid @RequestBody DonationRequest request) {
        // Requires ALUMNI role
        return ResponseEntity.ok(donationService.processDonation(request));
    }
}