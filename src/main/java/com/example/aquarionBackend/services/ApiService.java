package com.example.aquarionBackend.services;

import lombok.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;

    public <T> T get(String path, Class<T> responseType, Map<String, ?> params) {
        StringBuilder urlBuilder = new StringBuilder(path);
        if (params != null && !params.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        ResponseEntity<T> responseEntity = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, null, responseType);
        return responseEntity.getBody();
    }

    public <T> T post(String path, Object body, Class<T> responseType, HttpHeaders headers) {
        // Выполняем POST-запрос с переданным заголовком
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(path, new HttpEntity<>(body, headers), responseType);
        return responseEntity.getBody();
    }
}
