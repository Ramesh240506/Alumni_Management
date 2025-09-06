package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PendingRecordsResponse {
    private List<WorkExperienceResponse> pendingWorkExperience;
    private List<CourseResponse> pendingProfessionalCourses;
}

