package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "audit_log")
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logid")
    private Long logId;

    @Column(name = "targetRecordId", length = 36)
    private String targetRecordId; 

    @Column(name = "recordType", length = 50)
    private String recordType;

    @Column(name = "action", length = 50)
    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actionByUserld")
    private User actionByUser;
}