// package com.example.alumni.util;

// import com.example.alumni.entity.AlumniProfile;
// import com.example.alumni.entity.College;
// import com.example.alumni.entity.User;
// import com.example.alumni.entity.UserAffiliation;
// import com.example.alumni.exception.NotFoundException;
// import com.example.alumni.repository.AlumniProfileRepository;
// import com.example.alumni.repository.UserAffiliationRepository;
// import com.example.alumni.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import java.util.Optional;

// /**
//  * A utility component to abstract Spring Security's SecurityContextHolder.
//  * Provides easy, reusable methods to get details about the currently authenticated user.
//  */
// @Component
// @RequiredArgsConstructor
// public class SecurityUtil {

//     private final UserRepository userRepository;
//     private final AlumniProfileRepository alumniProfileRepository;
//     private final UserAffiliationRepository userAffiliationRepository; // Needed for admin lookup

//     /**
//      * Retrieves the username (email) of the currently authenticated user.
//      *
//      * @return An Optional containing the username, or empty if no user is authenticated.
//      */
//     public Optional<String> getCurrentUsername() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         if (authentication == null || !authentication.isAuthenticated()) {
//             return Optional.empty();
//         }

//         Object principal = authentication.getPrincipal();
//         if (principal instanceof UserDetails) {
//             return Optional.of(((UserDetails) principal).getUsername());
//         }
//         if (principal instanceof String) {
//             return Optional.of((String) principal);
//         }
        
//         return Optional.empty();
//     }

//     /**
//      * Retrieves the full User entity for the currently authenticated user from the database.
//      *
//      * @return The User entity.
//      * @throws NotFoundException if the user is not authenticated or not found in the database.
//      */
//     public User getCurrentUser() {
//         String username = getCurrentUsername()
//                 .orElseThrow(() -> new IllegalStateException("No authenticated user found"));
//         return userRepository.findByEmail(username)
//                 .orElseThrow(() -> new NotFoundException("User not found in database: " + username));
//     }

//     /**
//      * Retrieves the AlumniProfile entity for the currently authenticated alumni.
//      *
//      * @return The AlumniProfile entity.
//      * @throws NotFoundException if the user is not an alumnus or not found.
//      */
//     public AlumniProfile getCurrentAlumniProfile() {
//         User currentUser = getCurrentUser();
//         return alumniProfileRepository.findById(currentUser.getUserId())
//                 .orElseThrow(() -> new NotFoundException("Alumni profile not found for current user."));
//     }
    
//     /**
//      * Retrieves the College entity associated with the currently authenticated COLLEGE_ADMIN.
//      *
//      * @return The College entity the admin belongs to.
//      * @throws NotFoundException if the admin's affiliation is not found.
//      * @throws IllegalStateException if the user is not a COLLEGE_ADMIN.
//      */
//     public College getCurrentAdminCollege() {
//         User adminUser = getCurrentUser();
        
//         // Find the admin's affiliation to a college
//         UserAffiliation affiliation = userAffiliationRepository.findByUser(adminUser)
//             .orElseThrow(() -> new NotFoundException("Admin affiliation not found."));

//         // Assuming UserAffiliation has a getCollege() method or similar link
//         return affiliation.getCollege();
//     }
// }