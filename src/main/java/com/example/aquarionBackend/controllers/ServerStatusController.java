package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.exceptions.AppError;
import com.example.aquarionBackend.migrations.Store;
import com.example.aquarionBackend.models.dtos.ServerStatusDto;
import com.example.aquarionBackend.models.enums.ServerStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
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

    @Operation(summary = "Catch ml error service", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
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

    @Operation(summary = "Get server statuses", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @GetMapping("/servers")
    public ResponseEntity<ServerStatusDto> serverHealth(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ServerStatusDto.builder()
                        .access(store.getAccess().toString())
                        .management(store.getManagement().toString())
                        .mailServer(store.getMailSender().toString())
                        .mlServer(store.getMLServerStatus().toString())
                        .build());
    }

}
