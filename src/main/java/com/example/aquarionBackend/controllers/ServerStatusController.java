package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.migrations.Store;
import com.example.aquarionBackend.models.enums.ServerStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/health-check")
@Tag(name = "Server status API", description = "")
public class ServerStatusController {

    private final Store store;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/MLServer")
    public ResponseEntity<?> checkML(
            @RequestParam(name = "status")ServerStatus status){
        switch (status){
            case OK -> {}
            case ERROR -> store.setMLServerStatus(ServerStatus.ERROR);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
