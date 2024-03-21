package com.example.aquarionBackend.migrations;

import com.example.aquarionBackend.models.enums.ServerStatus;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Store {
    private ServerStatus MLServerStatus = ServerStatus.OK;
}
