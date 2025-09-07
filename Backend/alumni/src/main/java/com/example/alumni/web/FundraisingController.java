package com.example.alumni.web;

import com.example.alumni.dto.CampaignRequest;
import com.example.alumni.dto.CampaignResponse;
import com.example.alumni.service.FundraisingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fundraising")
@RequiredArgsConstructor
public class FundraisingController {
    
    private final FundraisingService fundraisingService;
    
    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns() {
        return ResponseEntity.ok(fundraisingService.getAllCampaigns());
    }

    @PostMapping("/campaigns")
    public ResponseEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignRequest request) {
        // Requires COLLEGE_ADMIN role
        CampaignResponse newCampaign = fundraisingService.createCampaign(request);
        return new ResponseEntity<>(newCampaign, HttpStatus.CREATED);
    }
}