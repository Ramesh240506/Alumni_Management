
package com.example.alumni.service;

import com.example.alumni.entity.Connection;

import java.util.List;

public interface ConnectionService {
    Connection sendRequest(String requesterId, String approverId);
    Connection approveRequest(String requesterId, String approverId, boolean approve);
    List<Connection> getMyConnections(String userId);
}
