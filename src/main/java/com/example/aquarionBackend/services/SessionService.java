package com.example.aquarionBackend.services;

import com.example.aquarionBackend.exceptions.ChatSessionNotFoundExc;
import com.example.aquarionBackend.exceptions.PermissionDeniedExc;
import com.example.aquarionBackend.models.dtos.requests.CloseSessionReq;
import com.example.aquarionBackend.models.dtos.requests.OpenSessionReq;
import com.example.aquarionBackend.models.dtos.responses.CloseSessionRes;
import com.example.aquarionBackend.models.dtos.responses.OpenSessionRes;
import com.example.aquarionBackend.models.entities.ChatSession;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final ChatSessionRepo chatSessionRepo;

    public OpenSessionRes openSession(OpenSessionReq req){
        LocalDateTime time = LocalDateTime.now();
        ChatSession chatSession = ChatSession.builder()
                .startTime(time)
                .FIO(req.getFIO())
                .mail(req.getMail())
                .build();
        chatSessionRepo.save(chatSession);
        OpenSessionRes res = OpenSessionRes.builder()
                .timestamp(time.toString())
                .sessionId(chatSession.getId().toString())
                .build();
        return res;
    }

    public CloseSessionRes closeSession(CloseSessionReq req){
        LocalDateTime time = LocalDateTime.now();
        ChatSession chatSession = chatSessionRepo.findById(req.getSessionId())
                .orElseThrow(()->new ChatSessionNotFoundExc(req.getSessionId()));
        if(!chatSession.getMail().equals(req.getMail())){
            throw new PermissionDeniedExc(req.getSessionId());
        }
        chatSession.setEndTime(time);
        chatSessionRepo.save(chatSession);
        CloseSessionRes res = CloseSessionRes.builder()
                .timestamp(time.toString())
                .sessionId(chatSession.getId().toString())
                .build();
        return res;
    }
}
