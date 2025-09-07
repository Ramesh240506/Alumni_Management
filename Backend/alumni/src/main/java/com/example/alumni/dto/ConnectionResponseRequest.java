package com.example.alumni.dto;

import com.example.alumni.entity.enums.ConnectionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionResponseRequest {

    @NotNull(message = "A status (ACCEPTED or REJECTED) is required to respond to a request")
    private ConnectionStatus status;
}