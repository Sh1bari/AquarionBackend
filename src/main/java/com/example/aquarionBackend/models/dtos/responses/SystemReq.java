package com.example.aquarionBackend.models.dtos.responses;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemReq {
    private List<String> systems;
}
