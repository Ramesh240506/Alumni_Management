package com.example.alumni.serviceimpl;

import com.example.alumni.dto.CampaignRequest;
import com.example.alumni.dto.CampaignResponse;
import com.example.alumni.entity.College;
import com.example.alumni.entity.FundraisingCampaign;
import com.example.alumni.entity.User;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.CollegeRepository;
import com.example.alumni.repository.FundraisingCampaignRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.FundraisingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundraisingServiceImpl implements FundraisingService {

    private final FundraisingCampaignRepository campaignRepository;
    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;

    @Override
    public List<CampaignResponse> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(this::mapToCampaignResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CampaignResponse createCampaign(CampaignRequest request) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() != UserRole.COLLEGE_ADMIN) {
            throw new UnauthorizedException("Only College Administrators can create fundraising campaigns.");
        }

        String collegeId = currentUser.getAffiliations().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("College admin is not affiliated with any college."))
                .getEntityId();
        
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new NotFoundException("Affiliated college not found."));

        FundraisingCampaign campaign = new FundraisingCampaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setGoalAmount(request.getGoalAmount());
        campaign.setStartDate(request.getStartDate());
        campaign.setEndDate(request.getEndDate());
        campaign.setCollege(college);

        FundraisingCampaign savedCampaign = campaignRepository.save(campaign);
        return mapToCampaignResponse(savedCampaign);
    }
    
    private CampaignResponse mapToCampaignResponse(FundraisingCampaign campaign) {
        return CampaignResponse.builder()
                .campaignId(campaign.getCampaignId())
                .title(campaign.getTitle())
                .description(campaign.getDescription())
                .goalAmount(campaign.getGoalAmount())
                .currentAmount(campaign.getCurrentAmount())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .status(campaign.getStatus())
                .build();
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found."));
    }
}