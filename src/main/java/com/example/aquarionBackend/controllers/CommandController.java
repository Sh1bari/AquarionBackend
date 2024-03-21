package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.configs.KafkaProducerConfig;
import com.example.aquarionBackend.configs.security.CustomUserDetails;
import com.example.aquarionBackend.exceptions.AppError;
import com.example.aquarionBackend.exceptions.CantSendMailExc;
import com.example.aquarionBackend.models.dtos.KafkaDto;
import com.example.aquarionBackend.models.dtos.MessageDto;
import com.example.aquarionBackend.models.dtos.SendToSupportDto;
import com.example.aquarionBackend.models.dtos.UrlDto;
import com.example.aquarionBackend.models.dtos.requests.SendToSupportReq;
import com.example.aquarionBackend.models.entities.Access;
import com.example.aquarionBackend.models.entities.Management;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.models.enums.MessagePattern;
import com.example.aquarionBackend.models.enums.SystemEnum;
import com.example.aquarionBackend.repositories.AccessRepo;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import com.example.aquarionBackend.repositories.ManagementRepo;
import com.example.aquarionBackend.services.CommandService;
import com.example.aquarionBackend.services.KafkaService;
import com.example.aquarionBackend.services.MessageService;
import com.example.aquarionBackend.services.MinioService;
import com.example.aquarionBackend.services.kafka.KafkaProducerService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
    private final MessageService messageService;
    private final CommandService commandService;
    private final KafkaService kafkaService;

    @SneakyThrows
    @Operation(summary = "Get access docx", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @GetMapping("/access")
    public ResponseEntity<UrlDto> getAccess(
            @RequestParam(name = "system") SystemEnum system,
            @RequestParam(name = "sessionId") UUID sessionId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        Access access = accessRepo.findFirstBySystem(system).orElse(null);
        String url = minioService.getPresignedUrlForFile(access.getFile().getPath(), 10000);
        UrlDto res = UrlDto.builder()
                .url(url)
                .build();
        messageService.createAccessMessage(sessionId, res);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @SneakyThrows
    @Operation(summary = "Get management docx", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @GetMapping("/management")
    public ResponseEntity<?> getManagement(
            @RequestParam(name = "system") SystemEnum system,
            @RequestParam(name = "sessionId") UUID sessionId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        Management management = managementRepo.findFirstBySystem(system).orElse(null);
        String url = minioService.getPresignedUrlForFile(management.getFile().getPath(), 1000);
        UrlDto res = UrlDto.builder()
                .url(url)
                .build();
        messageService.createManagementMessage(sessionId, res);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @Operation(summary = "Send message to support", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    //@Secured("ROLE_AUTHORIZED")
    @PostMapping("/send-to-support")
    public ResponseEntity<SendToSupportDto> sendToSupport(
            @RequestParam(name = "sessionId") UUID sessionId,
            @ModelAttribute SendToSupportReq req,
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        Message message = messageService.createSupportMessage(sessionId, req, files);
        try {
            commandService.sendToSupport(req, files);
            messageService.confirmReplyMessage(message);
        }catch (CantSendMailExc e){
            messageService.rejectReplyMessage(message);
            throw e;
        }
        SendToSupportDto res = SendToSupportDto.builder()
                .pattern(MessagePattern.WAITING_MESSAGE)
                .messageId(message.getId())
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @Operation(summary = "Send message to ml server", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})
    })
    @Secured("ROLE_AUTHORIZED")
    @PostMapping("/send-to-ml")
    public ResponseEntity<?> sendToML(
            @RequestParam(name = "sessionId") UUID sessionId,
            @RequestBody MessageDto messageDto,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        messageService.createMLMessage(sessionId, messageDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
