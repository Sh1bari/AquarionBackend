package com.example.aquarionBackend.models.dtos;

import com.example.aquarionBackend.models.entities.File;
import com.example.aquarionBackend.models.enums.MessageEnum;
import com.example.aquarionBackend.models.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageDto {
    private Long id;
    private Integer position;
    private List<File> files;
    private MessageEnum messageEnum;
    private MessageType messageType;
    private String message;
    private String reply;
    private LocalDateTime sent;
    private LocalDateTime received;
}
