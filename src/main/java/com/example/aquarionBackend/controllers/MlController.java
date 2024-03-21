package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.exceptions.MessageNotFoundExc;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.repositories.MessageRepo;
import com.example.aquarionBackend.services.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/ml")
@Tag(name = "ML server API", description = "")
public class MlController {
    private final MessageService messageService;
    private final MessageRepo messageRepo;

    @PostMapping("/error-message")
    public ResponseEntity<?> errorMessage(@RequestParam(name = "messageId")Long messageId){
        Message message = messageRepo.findById(messageId)
                        .orElseThrow(MessageNotFoundExc::new);
        messageService.rejectReplyMessage(message);
        return ResponseEntity.ok().build();
    }
}
