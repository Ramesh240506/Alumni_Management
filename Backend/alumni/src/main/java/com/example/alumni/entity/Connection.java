package com.example.alumni.entity;

import com.example.alumni.entity.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Connections")
@IdClass(ConnectionId.class)
@Getter
@Setter
public class Connection {

    @Id
    @Column(name = "requesterId", length = 36)
    private String requesterId;

    @Id
    @Column(name = "approverId", length = 36)
    private String approverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConnectionStatus status = ConnectionStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesterId", insertable = false, updatable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approverId", insertable = false, updatable = false)
    private User approver;
}