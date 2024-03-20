package com.example.aquarionBackend.models.dtos.responses;

import com.example.aquarionBackend.models.enums.MessagePattern;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenSessionRes {
    private String timestamp;
    private String sessionId;
    private MessagePattern pattern = MessagePattern.OPEN_SESSION;
}
