package com.example.alumni.serviceimpl;

import com.example.alumni.dto.ConnectionRequest;
import com.example.alumni.dto.ConnectionResponseRequest;
import com.example.alumni.entity.User;
import com.example.alumni.entity.UserConnection; // Corrected import
import com.example.alumni.entity.enums.ConnectionStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.UserConnectionRepository; // Corrected import
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    // --- THIS IS THE FIX ---
    private final UserConnectionRepository connectionRepository; // Use the renamed repository
    private final UserRepository userRepository;
    // --- END OF FIX ---

    @Override
    @Transactional
    public void sendConnectionRequest(String targetUserId) {
        User currentUser = getCurrentUser();
        
        if (currentUser.getRole() != UserRole.ALUMNI) {
            throw new UnauthorizedException("Only alumni can send connection requests.");
        }

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + targetUserId));
        
        if (targetUser.getRole() != UserRole.ALUMNI || 
            !currentUser.getAlumniProfile().getCollege().equals(targetUser.getAlumniProfile().getCollege())) {
            throw new UnauthorizedException("You can only connect with alumni from your own college.");
        }

        UserConnection newConnection = new UserConnection();
        newConnection.setRequesterId(currentUser.getUserId());
        newConnection.setApproverId(targetUserId);
        newConnection.setStatus(ConnectionStatus.PENDING);
        newConnection.setRequester(currentUser);
        newConnection.setApprover(targetUser);
        
        connectionRepository.save(newConnection);
    }

     @Override
    @Transactional
    public void respondToConnectionRequest(String requesterId, ConnectionResponseRequest responseRequest) { // <-- USE THE NEW DTO
        User currentUser = getCurrentUser(); // The approver

        UserConnection connection = connectionRepository.findById(new com.example.alumni.entity.ConnectionId(requesterId, currentUser.getUserId()))
                .orElseThrow(() -> new NotFoundException("Connection request from user " + requesterId + " not found."));

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new IllegalStateException("This connection request has already been responded to.");
        }

        connection.setStatus(responseRequest.getStatus()); // Get status from the new DTO
        connectionRepository.save(connection);
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
             throw new IllegalStateException("User is not authenticated.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }
}