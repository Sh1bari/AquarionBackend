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
    private ServerStatus mailSender = ServerStatus.OK;
    private ServerStatus access = ServerStatus.OK;
    private ServerStatus management = ServerStatus.OK;
}
