package com.example.aquarionBackend.services;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;
    public String uploadFile(String fileName, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            // Загружаем файл в MinIO
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(file.getContentType())
                    .build());
            return response.object();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPresignedUrlForFile(String fileName, int expirySeconds) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Проверяем, существует ли файл
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .build());

            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .expiry(expirySeconds, TimeUnit.SECONDS)
                            .method(Method.GET)
                            .build());
            // Создаем пресайнд URL для просмотра файла
            return url;
        } catch (MinioException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String uploadFile(String fileName, File file) {
        try {
            InputStream inputStream = new FileInputStream(file);

            // Загружаем файл в MinIO
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(inputStream, file.length(), -1)
                    .contentType(getContentType(file))
                    .build());
            return response.object();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getContentType(File file) {
        // Возвращает MIME тип файла
        return URLConnection.guessContentTypeFromName(file.getName());
    }
}
