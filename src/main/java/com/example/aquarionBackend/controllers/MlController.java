package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.exceptions.AppError;
import com.example.aquarionBackend.exceptions.MessageNotFoundExc;
import com.example.aquarionBackend.models.entities.ChatSession;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import com.example.aquarionBackend.repositories.MessageRepo;
import com.example.aquarionBackend.services.MessageService;
import com.example.aquarionBackend.webSocket.EventEnum;
import com.example.aquarionBackend.webSocket.EventMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/ml")
@Tag(name = "ML server API", description = "")
public class MlController {
    private final MessageService messageService;
    private final MessageRepo messageRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatSessionRepo chatSessionRepo;

    @Operation(summary = "Catch error messages from ml", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @PostMapping("/error-message")
    public ResponseEntity<?> errorMessage(@RequestParam(name = "messageId")Long messageId){
        Message message = messageRepo.findById(messageId)
                        .orElseThrow(MessageNotFoundExc::new);
        messageService.rejectReplyMessage(message);
        UUID session = chatSessionRepo.findChatSessionIdByMessageId(messageId);
        EventMessageDto eventMessageDto = EventMessageDto.builder()
                .event(EventEnum.MESSAGE_FAILED)
                .messageId(messageId)
                .build();
        messagingTemplate.convertAndSend("/topic/events/" + session, eventMessageDto);
        return ResponseEntity.ok().build();
    }
}
