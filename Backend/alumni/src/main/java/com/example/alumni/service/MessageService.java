package com.example.alumni.service;

import com.example.alumni.dto.MessageRequest;
import com.example.alumni.dto.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageResponse sendMessage(UUID senderId, MessageRequest request);
    List<MessageResponse> getConversation(UUID currentUserId, UUID otherUserId);
}
