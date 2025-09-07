package com.example.alumni.service;

import com.example.alumni.dto.MessageRequest;

public interface MessageService {
    void sendMessage(MessageRequest request);
}