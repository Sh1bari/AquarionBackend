package com.example.aquarionBackend.models.dtos.requests;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloseSessionReq {
    private String username;
    private UUID sessionId;
}
