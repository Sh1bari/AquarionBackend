package com.example.aquarionBackend.services.kafka;

import com.example.aquarionBackend.models.dtos.KafkaDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    @Value("${kafka.sendTopic}")
    private String topic;

    private final KafkaTemplate<String, KafkaDto> kafkaTemplate;

    public void sendMessage(KafkaDto message) {
        kafkaTemplate.send(topic, message);
    }
}
