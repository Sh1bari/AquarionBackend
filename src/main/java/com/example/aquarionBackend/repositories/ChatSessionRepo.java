package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.ChatSession;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatSessionRepo extends JpaRepository<ChatSession, UUID> {
    List<ChatSession> findAllByMailAndEndTimeIsNull(String mail);
}
