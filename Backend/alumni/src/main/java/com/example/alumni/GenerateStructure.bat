@echo off
REM === Main Application ===
echo package com.yourorg.alumni; > AlumniApplication.java
echo public class AlumniApplication { public static void main(String[] args){ System.out.println("Alumni Management System Running..."); } } >> AlumniApplication.java

REM === Config Files ===
for %%f in (SwaggerConfig DatabaseConfig AadhaarConfig SecurityConfig) do (
  echo package com.yourorg.alumni.config; > config\%%f.java
  echo public class %%f {} >> config\%%f.java
)

REM === Entities ===
for %%f in (User College Company CourseProvider AlumniProfile DigitalId AuditLog AcademicRecord WorkExperience ProfessionalCourse Message JobPosting Event InternshipFeedback FundraisingCampaign Donation Connection EventRsvp UserAffiliation) do (
  echo package com.yourorg.alumni.entity; > entity\%%f.java
  echo public class %%f {} >> entity\%%f.java
)

REM === Repositories ===
for %%f in (UserRepository CollegeRepository CompanyRepository CourseProviderRepository AlumniProfileRepository DigitalIdRepository AuditLogRepository AcademicRecordRepository WorkExperienceRepository ProfessionalCourseRepository MessageRepository JobPostingRepository EventRepository InternshipFeedbackRepository FundraisingCampaignRepository DonationRepository ConnectionRepository EventRsvpRepository UserAffiliationRepository) do (
  echo package com.yourorg.alumni.repository; > repository\%%f.java
  echo public interface %%f {} >> repository\%%f.java
)

REM === DTOs ===
for %%f in (LoginRequest RegisterRequest AadhaarVerificationRequest UserResponse AlumniProfileRequest AlumniProfileResponse AcademicRecordDto WorkExperienceRequest WorkExperienceResponse CourseRequest CourseResponse JobRequest JobResponse EventRequest EventResponse CampaignRequest CampaignResponse DonationRequest DonationResponse ConnectionRequest ConnectionResponse MessageRequest) do (
  echo package com.yourorg.alumni.dto; > dto\%%f.java
  echo public class %%f {} >> dto\%%f.java
)

REM === Services ===
for %%f in (UserService AuthService AlumniService CollegeService CompanyService CourseProviderService AcademicService WorkExperienceService ProfessionalCourseService JobService EventService FundraisingService DonationService ConnectionService MessageService VerificationService) do (
  echo package com.yourorg.alumni.service; > service\%%f.java
  echo public interface %%f {} >> service\%%f.java
)

REM === Service Implementations ===
for %%f in (UserServiceImpl AuthServiceImpl AlumniServiceImpl CollegeServiceImpl CompanyServiceImpl CourseProviderServiceImpl AcademicServiceImpl WorkExperienceServiceImpl ProfessionalCourseServiceImpl JobServiceImpl EventServiceImpl FundraisingServiceImpl DonationServiceImpl ConnectionServiceImpl MessageServiceImpl VerificationServiceImpl) do (
  echo package com.yourorg.alumni.service.impl; > serviceimpl\%%f.java
  echo public class %%f implements com.yourorg.alumni.service.%%~nf {} >> serviceimpl\%%f.java
)

REM === Controllers ===
for %%f in (AuthController AlumniController CollegeAdminController EmployerController JobController EventController FundraisingController DonationController ConnectionController MessageController) do (
  echo package com.yourorg.alumni.web; > web\%%f.java
  echo public class %%f {} >> web\%%f.java
)

REM === Security ===
for %%f in (JwtUtil JwtFilter SecurityConfig AadhaarOtpValidator) do (
  echo package com.yourorg.alumni.security; > security\%%f.java
  echo public class %%f {} >> security\%%f.java
)

REM === Utils ===
for %%f in (EmailUtil HashUtil DateUtil PdfGeneratorUtil MapUtil) do (
  echo package com.yourorg.alumni.util; > util\%%f.java
  echo public class %%f {} >> util\%%f.java
)

REM === Audit ===
for %%f in (AuditLog AuditService) do (
  echo package com.yourorg.alumni.audit; > audit\%%f.java
  echo public class %%f {} >> audit\%%f.java
)

REM === Exceptions ===
(for %%f in (ApiException NotFoundException UnauthorizedException ValidationException ConflictException) do (
  echo package com.yourorg.alumni.exception; > exception\%%f.java
  echo public class %%f extends RuntimeException {} >> exception\%%f.java
))
echo package com.yourorg.alumni.exception; > exception\GlobalExceptionHandler.java
echo public class GlobalExceptionHandler {} >> exception\GlobalExceptionHandler.java

echo All Java files created successfully!
pause
