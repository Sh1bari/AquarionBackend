package com.example.aquarionBackend.models.dtos;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerStatusDto {
    private String access;
    private String management;
    private String mlServer;
    private String mailServer;
}
