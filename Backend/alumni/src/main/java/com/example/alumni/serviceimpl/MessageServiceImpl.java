package com.example.alumni.serviceimpl;

import com.example.alumni.dto.MessageRequest;
import com.example.alumni.entity.Message;
import com.example.alumni.entity.User;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.MessageRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public void sendMessage(MessageRequest request) {
        User sender = getCurrentUser();
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new NotFoundException("Receiver user not found with ID: " + request.getReceiverId()));

        // Business logic can be added here, e.g., check if users are connected
        
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(request.getContent());
        
        messageRepository.save(message);
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found."));
    }
}