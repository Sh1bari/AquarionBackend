package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.System;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemRepo extends JpaRepository<System, Long> {
    List<System> findAllByColonyName(String colonyName);
}
