package com.example.alumni.web;

import com.example.alumni.dto.DonationRequest;
import com.example.alumni.dto.DonationResponse;
import com.example.alumni.service.DonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/donations") // Base path for donation-related operations
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    /**
     * POST /campaigns/{id}/donate – Donate to a campaign
     * (Note: The PDF specified /campaigns/{id}/donate, but for a dedicated DonationController,
     * it's often cleaner to map it under /donations but still reference the campaignId)
     * @param campaignId The UUID of the campaign to donate to.
     * @param donationRequest The request body containing donation details.
     * @return A DonationResponse object with the details of the created donation.
     */
    @PostMapping("/campaigns/{campaignId}/donate")
    public ResponseEntity<DonationResponse> donateToCampaign(
            @PathVariable("campaignId") UUID campaignId,
            @Valid @RequestBody DonationRequest donationRequest) {
        DonationResponse response = donationService.donateToCampaign(campaignId, donationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /donations/my – View personal donations (linked with alumniId)
     * (Note: In a real application, the userId would be extracted from the authenticated user's context,
     * not passed as a request parameter for security reasons. This is a placeholder.)
     * @param userId The UUID of the donor to retrieve donations for.
     * @return A list of DonationResponse objects for the specified donor.
     */
    @GetMapping("/my")
    public ResponseEntity<List<DonationResponse>> getMyDonations(@RequestParam("userId") UUID userId) {
        // IMPORTANT: In a production application, `userId` should be obtained from the
        // authenticated user's security context (e.g., JWT token), not from a request parameter.
        List<DonationResponse> donations = donationService.getMyDonations(userId);
        return ResponseEntity.ok(donations);
    }
}