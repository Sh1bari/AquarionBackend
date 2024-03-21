package com.example.aquarionBackend.webSocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventMessageDto {
    private EventEnum event;
    private Long messageId;
    private String message;
}
