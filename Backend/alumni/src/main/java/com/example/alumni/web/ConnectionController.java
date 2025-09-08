package com.example.alumni.web;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.dto.ConnectionRequest;
import com.example.alumni.dto.ConnectionResponse;
import com.example.alumni.dto.ConnectionResponseRequest;
import com.example.alumni.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/request/{targetUserId}")
    public ResponseEntity<ApiResponse> sendRequest(@PathVariable String targetUserId) {
        connectionService.sendConnectionRequest(targetUserId);
        return ResponseEntity.ok(new ApiResponse(true, "Connection request sent successfully."));
    }

     // --- THIS IS THE UPDATED METHOD ---
    @PutMapping("/respond/{requesterId}")
    public ResponseEntity<ApiResponse> respondToRequest(
            @PathVariable String requesterId,
            @Valid @RequestBody ConnectionResponseRequest responseRequest // <-- USE THE NEW DTO
    ) {
        connectionService.respondToConnectionRequest(requesterId, responseRequest); // Pass the new DTO
        String message = "Successfully " + responseRequest.getStatus().toString().toLowerCase() + " the connection request.";
        return ResponseEntity.ok(new ApiResponse(true, message));
    }
    // ... inside ConnectionController ...

// --- ADD THIS NEW ENDPOINT ---
@GetMapping("/requests/pending")
public ResponseEntity<List<ConnectionResponse>> getPendingRequests() {
    return ResponseEntity.ok(connectionService.getPendingRequests());
}
}