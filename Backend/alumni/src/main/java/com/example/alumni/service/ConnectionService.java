package com.example.alumni.service;

import com.example.alumni.dto.ConnectionRequest;
import com.example.alumni.dto.ConnectionResponse; // <-- ADD THIS IMPORT
import com.example.alumni.dto.ConnectionResponseRequest;

import java.util.List; // <-- ADD THIS IMPORT

public interface ConnectionService {

    void sendConnectionRequest(String targetUserId);

    void respondToConnectionRequest(String requesterId, ConnectionResponseRequest responseRequest);

    // --- THIS IS THE MISSING METHOD DECLARATION ---
    /**
     * Retrieves a list of pending connection requests for the authenticated user.
     * @return A list of ConnectionResponse DTOs.
     */
    List<ConnectionResponse> getPendingRequests();
}