package com.example.aquarionBackend.services;

import com.example.aquarionBackend.exceptions.ChatSessionNotFoundExc;
import com.example.aquarionBackend.exceptions.ServiceUnavailableExc;
import com.example.aquarionBackend.migrations.Store;
import com.example.aquarionBackend.models.dtos.KafkaDto;
import com.example.aquarionBackend.models.dtos.MessageDto;
import com.example.aquarionBackend.models.dtos.UrlDto;
import com.example.aquarionBackend.models.dtos.requests.SendToSupportReq;
import com.example.aquarionBackend.models.entities.ChatSession;
import com.example.aquarionBackend.models.entities.File;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.models.enums.MessageEnum;
import com.example.aquarionBackend.models.enums.MessageType;
import com.example.aquarionBackend.models.enums.ServerStatus;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import com.example.aquarionBackend.repositories.MessageRepo;
import com.example.aquarionBackend.services.kafka.KafkaProducerService;
import com.example.aquarionBackend.utils.JsonUtils;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Value("${serverName}")
    private String serverName;
    private final MessageRepo messageRepo;
    private final ChatSessionRepo sessionRepo;
    private final FileService fileService;
    private final KafkaProducerService kafkaProducerService;
    private final Store store;

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

    @Transactional
    public Message createSupportMessage(UUID sessionId, SendToSupportReq message, List<MultipartFile> files){
        LocalDateTime time = LocalDateTime.now();
        Message msg = Message.builder()
                .messageEnum(MessageEnum.PENDING)
                .messageType(MessageType.SENT_TO_MAIL)
                .messageText(JsonUtils.toJson(message))
                .receivedFromUserTime(time)
                .build();
        if(files != null) {
            files.forEach(o -> {
                File file = fileService.saveFile(o);
                msg.getFiles().add(file);
            });
        }
        return linkMessage(sessionId, msg);
    }
    @Transactional
    public void confirmReplyMessage(Message message){
        message.setSentToUserTime(LocalDateTime.now());
        message.setMessageEnum(MessageEnum.DONE);
        messageRepo.save(message);
    }

    @Transactional
    public void rejectReplyMessage(Message message){
        message.setSentToUserTime(LocalDateTime.now());
        message.setMessageEnum(MessageEnum.ERROR);
        messageRepo.save(message);
    }

    @Transactional
    public void confirmKafkaMessage(Message message, String payload){
        message.setSentToUserTime(LocalDateTime.now());
        message.setReplyText(payload);
        message.setMessageEnum(MessageEnum.DONE);
        messageRepo.save(message);
    }

    @Transactional
    public Message createMLMessage(UUID sessionId, MessageDto message){
        LocalDateTime time = LocalDateTime.now();
        Message msg = Message.builder()
                .messageEnum(MessageEnum.PENDING)
                .messageType(MessageType.SENT_TO_AI)
                .messageText(JsonUtils.toJson(message))
                .receivedFromUserTime(time)
                .build();
        Message entity = linkMessage(sessionId, msg);
        try {
            kafkaProducerService.sendMessage(KafkaDto.builder()
                    .messageId(entity.getId())
                    .payload(message.getMessage())
                    .build());
            store.setMLServerStatus(ServerStatus.OK);
        }catch (Exception e){
            store.setMLServerStatus(ServerStatus.ERROR);
            throw new ServiceUnavailableExc();
        }
        return entity;
    }


}
