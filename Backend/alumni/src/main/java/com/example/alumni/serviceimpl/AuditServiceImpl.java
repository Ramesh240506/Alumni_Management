package com.example.alumni.serviceimpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.alumni.entity.AuditLog;
import com.example.alumni.entity.User;
import com.example.alumni.repository.AuditLogRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.AuditService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Override
    public void log(String action, String recordType, String targetRecordId, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setRecordType(recordType);
        log.setTargetRecordId(targetRecordId);
        log.setDetails(details);
        
        // Try to get the authenticated user, but don't fail if it's a system event
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
                User user = userRepository.findByEmail(authentication.getName()).orElse(null);
                log.setActionByUser(user);
            }
        } catch (Exception e) {
            // Ignore if no user is authenticated
        }
        
        auditLogRepository.save(log);
    }
}