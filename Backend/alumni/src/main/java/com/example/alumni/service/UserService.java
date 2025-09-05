package com.example.alumni.service;

import com.example.alumni.dto.UserResponse;
import java.util.List;

public interface UserService {

    /**
     * Retrieves a list of all users in the system.
     * (In a real application, this should be paginated).
     * @return A list of UserResponse DTOs.
     */
    List<UserResponse> getAllUsers();

    /**
     * Retrieves a single user by their ID.
     * @param userId The UUID of the user.
     * @return A UserResponse DTO.
     */
    UserResponse getUserById(String userId);

    // You could add more methods here for admin functionalities, e.g.:
    // UserResponse updateUserRole(String userId, UserRole newRole);
    // void deactivateUser(String userId);
}