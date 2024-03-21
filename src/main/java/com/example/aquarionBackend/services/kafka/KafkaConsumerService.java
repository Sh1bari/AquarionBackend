package com.example.aquarionBackend.services.kafka;

import com.example.aquarionBackend.exceptions.MessageNotFoundExc;
import com.example.aquarionBackend.models.dtos.KafkaDto;
import com.example.aquarionBackend.models.entities.ChatSession;
import com.example.aquarionBackend.models.entities.Message;
import com.example.aquarionBackend.repositories.ChatSessionRepo;
import com.example.aquarionBackend.repositories.MessageRepo;
import com.example.aquarionBackend.services.MessageService;
import com.example.aquarionBackend.webSocket.EventEnum;
import com.example.aquarionBackend.webSocket.EventMessageDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final ChatSessionRepo chatSessionRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final MessageRepo messageRepo;
    @KafkaListener(topics = "${kafka.readTopic}", groupId = "2a")
    public void consumeMessage(ConsumerRecord<String, KafkaDto> message) {
        log.warn("qwe");
        UUID session = chatSessionRepo.findChatSessionIdByMessageId(message.value().getMessageId());
        EventMessageDto eventMessageDto = EventMessageDto.builder()
                .event(EventEnum.MESSAGE_SUCCESS)
                .messageId(message.value().getMessageId())
                .message(message.value().getPayload())
                .build();
        Message message1 = messageRepo.findById(message.value().getMessageId())
                        .orElseThrow(MessageNotFoundExc::new);
        messageService.confirmKafkaMessage(message1, message.value().getPayload());
        messagingTemplate.convertAndSend("/topic/events/" + session, eventMessageDto);
    }
}
