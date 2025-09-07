package com.example.alumni.web;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.dto.MessageRequest;
import com.example.alumni.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok(new ApiResponse(true, "Message sent successfully."));
    }
}