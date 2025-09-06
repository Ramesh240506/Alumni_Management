
package com.example.alumni.serviceimpl;

import com.example.alumni.dto.MessageRequest;
import com.example.alumni.dto.MessageResponse;
import com.example.alumni.entity.Message;
import com.example.alumni.repository.MessageRepository;
import com.example.alumni.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public MessageResponse sendMessage(UUID senderId, MessageRequest request) {
        Message message = Message.builder()
                .sender(senderId)
                .receiver(request.getReceiverId())
                .content(request.getContent())
                .build();

        Message saved = messageRepository.save(message);
        return toResponse(saved);
    }

    @Override
    public List<MessageResponse> getConversation(UUID currentUserId, UUID otherUserId) {
        return messageRepository.getConversation(currentUserId, otherUserId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .messageId(message.getMessageId())
                .senderId(message.getSender())
                .receiverId(message.getReceiver())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
