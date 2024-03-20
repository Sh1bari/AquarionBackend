package com.example.aquarionBackend.services;

import com.example.aquarionBackend.repositories.MessageRepo;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;


}
