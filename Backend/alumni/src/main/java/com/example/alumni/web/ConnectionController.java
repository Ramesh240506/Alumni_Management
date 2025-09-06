
// ConnectionController.java
package com.example.alumni.web;

import com.example.alumni.entity.Connection;
import com.example.alumni.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    // POST /connections/request/{alumniId}?requesterId=123
    @PostMapping("/request/{alumniId}")
    public Connection sendRequest(@PathVariable String alumniId,
                                  @RequestParam String requesterId) {
        return connectionService.sendRequest(requesterId, alumniId);
    }

    // POST /connections/approve/{requesterId}/{approverId}?approve=true
    @PostMapping("/approve/{requesterId}/{approverId}")
    public Connection approveRequest(@PathVariable String requesterId,
                                     @PathVariable String approverId,
                                     @RequestParam boolean approve) {
        return connectionService.approveRequest(requesterId, approverId, approve);
    }

    // GET /connections/my?userId=123
    @GetMapping("/my")
    public List<Connection> getMyConnections(@RequestParam String userId) {
        return connectionService.getMyConnections(userId);
    }
}

