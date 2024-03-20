package com.example.aquarionBackend.services;

import com.example.aquarionBackend.models.dtos.MessageDto;
import com.example.aquarionBackend.repositories.MessageRepo;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Value("${serverName}")
    private String serverName;
    private final MessageRepo messageRepo;

    //public MessageDto


}
