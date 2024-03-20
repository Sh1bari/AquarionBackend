package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.Message;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
