package com.example.aquarionBackend.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendToSupportReq {
    private String mail;
    private String title;
    private String message;
    private String timestamp;
}
