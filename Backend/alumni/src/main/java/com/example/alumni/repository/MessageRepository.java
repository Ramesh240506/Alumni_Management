package com.example.alumni.repository;

import com.example.alumni.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    // Find messages between two users
    List<Message> findBySender_UserIdAndReceiver_UserIdOrSender_UserIdAndReceiver_UserIdOrderBySentAtAsc(
        String userId1, String userId2, String userId2_again, String userId1_again);
}