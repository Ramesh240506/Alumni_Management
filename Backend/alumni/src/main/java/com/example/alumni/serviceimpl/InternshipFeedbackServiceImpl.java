package com.example.alumni.serviceimpl;

import com.example.alumni.dto.InternshipFeedbackRequest;
import com.example.alumni.dto.InternshipFeedbackResponse;
import com.example.alumni.entity.Company;
import com.example.alumni.entity.InternshipFeedback;
import com.example.alumni.entity.User;
import com.example.alumni.exception.ResourceNotFoundException;
import com.example.alumni.repository.CompanyRepository;
import com.example.alumni.repository.InternshipFeedbackRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.InternshipFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternshipFeedbackServiceImpl implements InternshipFeedbackService {

    private final InternshipFeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public InternshipFeedbackResponse submitFeedback(InternshipFeedbackRequest request, String internUserId, String reviewerId) {
        User intern = userRepository.findById(internUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Intern user not found with id: " + internUserId));

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer user not found with id: " + reviewerId));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));

        InternshipFeedback feedback = new InternshipFeedback();
        feedback.setInternUser(intern);
        feedback.setReviewer(reviewer);
        feedback.setCompany(company);
        feedback.setFeedbackContent(request.getFeedbackContent());

        InternshipFeedback savedFeedback = feedbackRepository.save(feedback);
        return mapToFeedbackResponse(savedFeedback);
    }

    private InternshipFeedbackResponse mapToFeedbackResponse(InternshipFeedback feedback) {
        // Using .getEmail() because it is a guaranteed field on your User entity
        String internName = feedback.getInternUser().getEmail();
        String reviewerName = feedback.getReviewer().getEmail();

        return InternshipFeedbackResponse.builder()
                .feedbackId(feedback.getFeedbackId())
                .internName(internName)
                .companyName(feedback.getCompany().getName())
                .feedbackContent(feedback.getFeedbackContent())
                .reviewerName(reviewerName)
                .createdAt(feedback.getCreatedAt())
                .build();
    }
}