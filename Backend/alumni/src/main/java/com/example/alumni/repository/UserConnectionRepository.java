package com.example.alumni.repository;

import com.example.alumni.entity.UserConnection; // <-- UPDATED IMPORT
import com.example.alumni.entity.ConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, ConnectionId> { // <-- UPDATED ENTITY
    
    List<UserConnection> findByApproverIdAndStatus(String approverId, com.example.alumni.entity.enums.ConnectionStatus status);
}