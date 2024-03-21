package com.example.aquarionBackend.webSocket;

import com.example.aquarionBackend.configs.security.CustomUserDetails;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {
    @MessageMapping("/events/{sessionId}")
    @SendTo("/topic/events/{sessionId}")
    public String sendSegments(@Payload String data,
                                             @DestinationVariable("deviceId") String deviceId,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        return "";
    }
}
