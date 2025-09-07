package com.example.alumni.dto;

import com.example.alumni.entity.enums.ConnectionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionResponse {
    private UserInfo user;
    private ConnectionStatus status;

    @Data
    @Builder
    public static class UserInfo {
        private String userId;
        private String firstName;
        private String lastName;
    }
}