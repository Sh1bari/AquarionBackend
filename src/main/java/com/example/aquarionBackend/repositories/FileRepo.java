package com.example.aquarionBackend.repositories;

import com.example.aquarionBackend.models.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
