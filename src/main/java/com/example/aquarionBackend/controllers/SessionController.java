package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.configs.security.CustomUserDetails;
import com.example.aquarionBackend.exceptions.AppError;
import com.example.aquarionBackend.models.dtos.requests.CloseSessionReq;
import com.example.aquarionBackend.models.dtos.requests.OpenSessionReq;
import com.example.aquarionBackend.models.dtos.UrlDto;
import com.example.aquarionBackend.models.dtos.responses.CloseSessionRes;
import com.example.aquarionBackend.models.dtos.responses.OpenSessionRes;
import com.example.aquarionBackend.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("")
@Tag(name = "Session API", description = "")
public class SessionController {

    private final SessionService sessionService;

    @Operation(summary = "Open chat session", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @PostMapping("/session/open")
    public ResponseEntity<OpenSessionRes> openSession(
            @RequestBody OpenSessionReq req,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        OpenSessionRes res = sessionService.openSession(req, userDetails.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Close chat session", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))}),
            @ApiResponse(responseCode = "403", description = "Permission denied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @PostMapping("/session/close")
    public ResponseEntity<CloseSessionRes> closeSession(
            @RequestBody CloseSessionReq sessionId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        CloseSessionRes res = sessionService.closeSession(sessionId, userDetails.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
