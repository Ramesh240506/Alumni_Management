package com.example.alumni.web;

//import com.example.alumni.dto.Campai;
//import com.example.alumni.dto.CampaignResponse;
//import com.example.alumni.service.FundraisingService;
import com.example.alumni.dto.CampaignRequest;
import com.example.alumni.dto.CampaignResponse;
import com.example.alumni.service.FundraisingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class FundraisingController {

    private final FundraisingService fundraisingService;

    @PostMapping
    public ResponseEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignRequest campaignRequest) {
        CampaignResponse response = fundraisingService.createCampaign(campaignRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns() {
        List<CampaignResponse> campaigns = fundraisingService.getAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponse> getCampaignById(@PathVariable("id") UUID id) {
        CampaignResponse campaign = fundraisingService.getCampaignById(id);
        return ResponseEntity.ok(campaign);
    }
}