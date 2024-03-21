package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.AccessLogItemEntity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccessLogRepo extends JpaRepository<AccessLogItemEntity, UUID> {
}
