package com.example.alumni.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ConnectionId implements Serializable {
    private String requesterId;
    private String approverId;
}