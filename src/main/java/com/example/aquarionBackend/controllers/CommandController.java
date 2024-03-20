package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.exceptions.AppError;
import com.example.aquarionBackend.models.dtos.UrlDto;
import com.example.aquarionBackend.models.entities.Access;
import com.example.aquarionBackend.models.entities.Management;
import com.example.aquarionBackend.models.enums.SystemEnum;
import com.example.aquarionBackend.repositories.AccessRepo;
import com.example.aquarionBackend.repositories.ManagementRepo;
import com.example.aquarionBackend.services.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("")
@Tag(name = "Command API", description = "")
public class CommandController {
    private final MinioService minioService;
    private final AccessRepo accessRepo;
    private final ManagementRepo managementRepo;

    @Operation(summary = "Get access docx", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @GetMapping("/access")
    public ResponseEntity<UrlDto> getAccess(
            @RequestParam(name = "system") SystemEnum system){
        String res = "";
        try {
            Access access = accessRepo.findFirstBySystem(system).orElse(null);
            res = minioService.getPresignedUrlForFile(access.getFile().getPath(), 10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UrlDto.builder()
                        .url(res)
                        .build());
    }

    @Operation(summary = "Get management docx", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @GetMapping("/management")
    public ResponseEntity<?> getManagement(
            @RequestParam(name = "system") SystemEnum system){
        String res = "";
        try {
            Management management = managementRepo.findFirstBySystem(system).orElse(null);
            res = minioService.getPresignedUrlForFile(management.getFile().getPath(), 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UrlDto.builder()
                        .url(res)
                        .build());
    }
}
