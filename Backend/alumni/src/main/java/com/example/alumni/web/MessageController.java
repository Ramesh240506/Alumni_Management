package com.example.alumni.web;

import com.example.alumni.dto.MessageRequest;
import com.example.alumni.dto.MessageResponse;
import com.example.alumni.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public MessageResponse sendMessage(
            @AuthenticationPrincipal(expression = "id") UUID senderId, // assuming JWT contains userId
            @RequestBody MessageRequest request) {
        return messageService.sendMessage(senderId, request);
    }

    @GetMapping("/conversation/{userId}")
    public List<MessageResponse> getConversation(
            @AuthenticationPrincipal(expression = "id") UUID currentUserId,
            @PathVariable UUID userId) {
        return messageService.getConversation(currentUserId, userId);
    }
}
