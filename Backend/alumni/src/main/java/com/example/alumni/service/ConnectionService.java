package com.example.alumni.service;

import com.example.alumni.dto.ConnectionResponseRequest; // <-- UPDATED IMPORT

public interface ConnectionService {
    void sendConnectionRequest(String targetUserId);
    void respondToConnectionRequest(String requesterId, ConnectionResponseRequest responseRequest); // <-- USE THE NEW DTO
}