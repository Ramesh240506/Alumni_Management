

package com.example.alumni.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class MessageRequest {
    private UUID receiverId;
    private String content;
}
