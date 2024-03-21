package com.example.aquarionBackend.models.dtos;

import com.example.aquarionBackend.models.enums.MessagePattern;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendToSupportDto {
    private Long messageId;
    private MessagePattern pattern;
}
