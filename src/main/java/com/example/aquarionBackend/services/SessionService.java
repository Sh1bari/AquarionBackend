package com.example.aquarionBackend.services;

import com.example.aquarionBackend.exceptions.ChatSessionNotFoundExc;
import com.example.aquarionBackend.exceptions.PermissionDeniedExc;
import com.example.aquarionBackend.models.dtos.requests.CloseSessionReq;
import com.example.aquarionBackend.models.dtos.requests.OpenSessionReq;
import com.example.aquarionBackend.models.dtos.responses.CloseSessionRes;
import com.example.aquarionBackend.models.dtos.responses.OpenSessionRes;
import com.example.aquarionBackend.models.entities.ChatSession;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.models.enums.MessageEnum;
import com.example.aquarionBackend.models.enums.MessagePattern;
import com.example.aquarionBackend.models.enums.MessageType;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final ChatSessionRepo chatSessionRepo;

    public OpenSessionRes openSession(OpenSessionReq req){
        LocalDateTime time = LocalDateTime.now();
        Message message = Message.builder()
                .messageEnum(MessageEnum.DONE)
                .messageType(MessageType.NON_DECLARED)
                .position(1)
                .sent(time)
                .reply(MessagePattern.OPEN_SESSION.toString())
                .build();
        ChatSession chatSession = ChatSession.builder()
                .startTime(time)
                .FIO(req.getFIO())
                .mail(req.getMail())
                .messages(List.of(message))
                .build();
        chatSessionRepo.save(chatSession);
        OpenSessionRes res = OpenSessionRes.builder()
                .timestamp(time.toString())
                .pattern(MessagePattern.OPEN_SESSION)
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
