package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.Access;
import com.example.aquarionBackend.models.enums.SystemEnum;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessRepo extends JpaRepository<Access, Long> {
    Optional<Access> findFirstBySystem(SystemEnum system);
}
