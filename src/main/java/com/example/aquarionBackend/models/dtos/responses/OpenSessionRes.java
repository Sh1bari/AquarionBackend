package com.example.aquarionBackend.models.dtos.responses;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenSessionRes {
    private String timestamp;
    private String sessionId;
}
