package com.example.aquarionBackend.models.dtos;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportDto implements Serializable {
    private String message;
}
