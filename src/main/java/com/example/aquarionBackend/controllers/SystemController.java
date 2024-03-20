package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.configs.ConstantsConfig;
import com.example.aquarionBackend.configs.security.CustomUserDetails;
import com.example.aquarionBackend.exceptions.AppError;
import com.example.aquarionBackend.models.dtos.requests.OpenSessionReq;
import com.example.aquarionBackend.models.dtos.responses.OpenSessionRes;
import com.example.aquarionBackend.models.dtos.responses.SystemReq;
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

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("")
@Tag(name = "System API", description = "")
public class SystemController {
    private final ConstantsConfig constantsConfig;
    @Operation(summary = "Get systems", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @PostMapping("/systems")
    public ResponseEntity<SystemReq> getSystems(
            @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SystemReq.builder()
                        .systems(constantsConfig.getSystems())
                        .build());
    }
}
