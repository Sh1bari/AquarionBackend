package com.example.aquarionBackend.webSocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventMessageDto {
    private EventEnum event;
    private String messageId;
}
