package com.example.aquarionBackend.models.dtos.responses;

import com.example.aquarionBackend.models.enums.MessagePattern;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Getter
@Setter
@Builder
@AllArgsConstructor
@Jacksonized
public class OpenSessionRes {
    String timestamp;
    String sessionId;
    MessagePattern pattern = MessagePattern.OPEN_SESSION;
}
