package com.example.alumni.entity;

import com.example.alumni.entity.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "connections") // The database table name remains 'connections'
@IdClass(ConnectionId.class)
@Getter
@Setter
public class UserConnection { // <-- CLASS RENAMED

    @Id
    @Column(name = "requester_id")
    private String requesterId;

    @Id
    @Column(name = "approver_id")
    private String approverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectionStatus status = ConnectionStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", insertable = false, updatable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", insertable = false, updatable = false)
    private User approver;
}