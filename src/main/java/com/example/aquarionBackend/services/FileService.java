package com.example.aquarionBackend.services;

import com.example.aquarionBackend.models.entities.File;
import com.example.aquarionBackend.repositories.FileRepo;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${minio.bucket}")
    private String bucket;
    private final FileRepo fileRepo;
    private final MinioService minioService;

    @Transactional
    public File saveFile(MultipartFile multipartFile){
        LocalDateTime time = LocalDateTime.now();
        String filename = time + "_" + multipartFile.getOriginalFilename();
        String path = minioService.uploadFile(filename, multipartFile);
        File fileEntity = File.builder()
                .fileName(filename)
                .bucket(bucket)
                .date(time)
                .path(path)
                .build();
        fileRepo.save(fileEntity);
        return fileEntity;
    }
    @Transactional
    public File saveFile(java.io.File multipartFile){
        LocalDateTime time = LocalDateTime.now();
        String filename = time + "_" + multipartFile.getName();
        String path = minioService.uploadFile(filename, multipartFile);
        File fileEntity = File.builder()
                .fileName(filename)
                .bucket(bucket)
                .date(time)
                .path(path)
                .build();
        fileRepo.save(fileEntity);
        return fileEntity;
    }
}
