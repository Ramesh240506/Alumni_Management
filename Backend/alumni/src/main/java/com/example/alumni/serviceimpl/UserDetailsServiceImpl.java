package com.example.alumni.serviceimpl;

import com.example.alumni.entity.User;
import com.example.alumni.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for loading user-specific data for Spring Security.
 * This class is the bridge between our User entity and Spring Security's authentication mechanism.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Locates the user based on the username (in our case, email).
     * @param username The email address identifying the user whose data is required.
     * @return A UserDetails object containing the user's information.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // --- DEBUG LOG 1 ---
        // This will print to the console when Spring Security calls this method.
        System.out.println("\n--- [DEBUG] UserDetailsServiceImpl: Attempting to load user by email: " + username + " ---\n");

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    // --- DEBUG LOG 2 ---
                    // This will print if the user is NOT found in the database.
                    System.err.println("\n--- [DEBUG] UserDetailsServiceImpl: FAILED to find user with email: " + username + " ---\n");
                    return new UsernameNotFoundException("User not found with email: " + username);
                });

        // --- DEBUG LOG 3 ---
        // This will print if the user IS found, showing their status.
        System.out.println("\n--- [DEBUG] UserDetailsServiceImpl: User FOUND. Email: " + user.getEmail() + " | Is Active: " + user.isActive() + " ---\n");

        // Converts our User entity into a Spring Security UserDetails object.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(user.getRole().name())
                .accountExpired(false)
                .accountLocked(!user.isActive())
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }
}