package com.example.alumni.serviceimpl;

import com.example.alumni.dto.ConnectionRequest;
import com.example.alumni.dto.ConnectionResponse;
import com.example.alumni.dto.ConnectionResponseRequest;
import com.example.alumni.entity.User;
import com.example.alumni.entity.UserConnection;
import com.example.alumni.entity.enums.ConnectionStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.UserConnectionRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final UserConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void sendConnectionRequest(String targetUserId) {
        User currentUser = getCurrentUser();
        // ... (rest of the method is correct)
    }

    @Override
    @Transactional
    public void respondToConnectionRequest(String requesterId, ConnectionResponseRequest responseRequest) {
        User currentUser = getCurrentUser(); // The approver
        // ... (rest of the method is correct)
    }
    
    // --- THIS IS THE CORRECT, NON-DUPLICATED METHOD ---
    @Override
    public List<ConnectionResponse> getPendingRequests() {
        User currentUser = getCurrentUser();
        List<UserConnection> requests = connectionRepository.findByApproverIdAndStatus(currentUser.getUserId(), ConnectionStatus.PENDING);
        return requests.stream()
                .map(this::mapToConnectionResponse)
                .collect(Collectors.toList());
    }

    // --- HELPER METHODS (Only one copy of each) ---
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
             throw new IllegalStateException("User is not authenticated.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }
    
    private ConnectionResponse mapToConnectionResponse(UserConnection connection) {
        User requester = connection.getRequester();
        ConnectionResponse.UserInfo userInfo = ConnectionResponse.UserInfo.builder()
            .userId(requester.getUserId())
            .firstName(requester.getAlumniProfile().getFirstName())
            .lastName(requester.getAlumniProfile().getLastName())
            .build();
    
        return ConnectionResponse.builder()
            .user(userInfo)
            .status(connection.getStatus())
            .build();
    }
}