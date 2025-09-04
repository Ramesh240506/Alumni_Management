package com.example.alumni.entity;

import com.example.alumni.entity.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "connection")
@Data
public class Connection {

    @Id
    @Column(name = "requesterld", length = 36)
    private String requesterId;

    @Id
    @Column(name = "approverld", length = 36)
    private String approverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesterld", insertable = false, updatable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approverld", insertable = false, updatable = false)
    private User approver;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConnectionStatus status;
}