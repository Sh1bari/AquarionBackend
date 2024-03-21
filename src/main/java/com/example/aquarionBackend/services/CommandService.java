package com.example.aquarionBackend.services;

import com.example.aquarionBackend.exceptions.CantSendMailExc;
import com.example.aquarionBackend.models.dtos.SupportDto;
import com.example.aquarionBackend.models.dtos.requests.SendToSupportReq;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandService {
    private final MessageService messageService;
    private final ApiService apiService;
    @Value("${supportServiceUrl}")
    private String supportServiceUrl;

    public void sendToSupport(SendToSupportReq req, List<MultipartFile> files){

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    // Используйте ByteArrayResource для передачи файла
                    ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename();
                        }
                    };
                    body.add("files", resource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        body.add("mail", req.getMail());
        body.add("title", req.getTitle());
        body.add("message", req.getMessage());
        body.add("timestamp", req.getTimestamp());
        try {
            SupportDto res = apiService.post(supportServiceUrl+"/send-to-support", body, SupportDto.class , headers);
            System.out.println(res);
            // Обработка успешного ответа
        } catch (HttpClientErrorException e) {
            log.error(e.toString());
        } catch (HttpServerErrorException e) {
            log.error(e.toString());
            throw new CantSendMailExc();
        } catch (RestClientException e) {
            log.warn(e.toString());
        }
    }

    public void sendToMl(){

    }
}
