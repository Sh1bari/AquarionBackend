package com.example.aquarionBackend.services;

import com.example.aquarionBackend.exceptions.ChatSessionNotFoundExc;
import com.example.aquarionBackend.models.dtos.UrlDto;
import com.example.aquarionBackend.models.entities.ChatSession;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.models.enums.MessageEnum;
import com.example.aquarionBackend.models.enums.MessageType;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import com.example.aquarionBackend.repositories.MessageRepo;
import com.example.aquarionBackend.utils.JsonUtils;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Value("${serverName}")
    private String serverName;
    private final MessageRepo messageRepo;
    private final ChatSessionRepo sessionRepo;

    @Transactional
    public Message linkMessage(UUID sessionId, Message message){
        ChatSession session = sessionRepo.findById(sessionId)
                .orElseThrow(()->new ChatSessionNotFoundExc(sessionId));
        Integer maxPosition = session.getMessages().stream()
                .map(Message::getPosition)
                .max(Integer::compare)
                .orElse(0);
        message.setPosition(maxPosition+1);
        messageRepo.save(message);
        session.getMessages().add(message);
        sessionRepo.save(session);
        return message;
    }

    @Transactional
    public void createAccessMessage(UUID sessionId, UrlDto reply){
        LocalDateTime time = LocalDateTime.now();
        Message message = Message.builder()
                .messageEnum(MessageEnum.DONE)
                .messageType(MessageType.GET_ACCESS_COMMAND)
                .replyText(JsonUtils.toJson(reply))
                .receivedFromUserTime(time)
                .sentToUserTime(time)
                .build();
        linkMessage(sessionId, message);
    }

    @Transactional
    public void createManagementMessage(UUID sessionId, UrlDto reply){
        LocalDateTime time = LocalDateTime.now();
        Message message = Message.builder()
                .messageEnum(MessageEnum.DONE)
                .messageType(MessageType.GET_MANAGEMENT_COMMAND)
                .replyText(JsonUtils.toJson(reply))
                .receivedFromUserTime(time)
                .sentToUserTime(time)
                .build();
        linkMessage(sessionId, message);
    }


}
