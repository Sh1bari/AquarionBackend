package com.example.aquarionBackend.models.dtos.responses;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloseSessionRes {
    private String sessionId;
    private String timestamp;
}
