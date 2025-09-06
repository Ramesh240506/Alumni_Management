
package com.example.alumni.serviceimpl;

import com.example.alumni.entity.Connection;
import com.example.alumni.entity.ConnectionId;
import com.example.alumni.entity.enums.ConnectionStatus;
import com.example.alumni.repository.ConnectionRepository;
import com.example.alumni.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Override
    public Connection sendRequest(String requesterId, String approverId) {
        if (connectionRepository.existsByRequesterIdAndApproverId(requesterId, approverId)) {
            throw new RuntimeException("Connection request already exists");
        }

        Connection connection = new Connection();
        connection.setRequesterId(requesterId);
        connection.setApproverId(approverId);
        connection.setStatus(ConnectionStatus.PENDING);

        return connectionRepository.save(connection);
    }

    @Override
    public Connection approveRequest(String requesterId, String approverId, boolean approve) {
        ConnectionId id = new ConnectionId(requesterId, approverId);

        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection request not found"));

        connection.setStatus(approve ? ConnectionStatus.APPROVED : ConnectionStatus.REJECTED);
        return connectionRepository.save(connection);
    }

    @Override
    public List<Connection> getMyConnections(String userId) {
        return connectionRepository.findByRequesterIdOrApproverId(userId, userId);
    }
}
