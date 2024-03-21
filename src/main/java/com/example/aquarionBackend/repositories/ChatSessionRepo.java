package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.ChatSession;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatSessionRepo extends JpaRepository<ChatSession, UUID> {
    List<ChatSession> findAllByMailAndEndTimeIsNull(String mail);
    @Query("SELECT c.id FROM ChatSession c JOIN c.messages m WHERE m.id = :messageId")
    UUID findChatSessionIdByMessageId(@Param("messageId") Long messageId);
}
