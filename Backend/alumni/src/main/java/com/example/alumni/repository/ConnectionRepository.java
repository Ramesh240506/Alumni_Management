
package com.example.alumni.repository;

import com.example.alumni.entity.Connection;
import com.example.alumni.entity.ConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, ConnectionId> {

    // Fetch all connections where a user is either requester or approver
    List<Connection> findByRequesterIdOrApproverId(String requesterId, String approverId);

    // Optional: check if a connection already exists
    boolean existsByRequesterIdAndApproverId(String requesterId, String approverId);
}
