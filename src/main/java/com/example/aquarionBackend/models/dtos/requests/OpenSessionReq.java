package com.example.aquarionBackend.models.dtos.requests;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenSessionReq {
    private String FIO;
    private String mail;
}
