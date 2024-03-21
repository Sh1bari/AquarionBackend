package com.example.aquarionBackend.services.kafka;

import com.example.aquarionBackend.models.dtos.KafkaDto;
import lombok.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${kafka.readTopic}", groupId = "2a")
    public void consumeMessage(ConsumerRecord<String, KafkaDto> message) {
        System.out.println("Received message: " + message.value().getPayload());
    }
}
