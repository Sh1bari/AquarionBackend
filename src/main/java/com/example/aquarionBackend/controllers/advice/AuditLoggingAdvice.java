package com.example.aquarionBackend.controllers.advice;

import com.example.aquarionBackend.configs.security.CustomUserDetails;
import com.example.aquarionBackend.models.entities.AccessLogItemEntity;
import com.example.aquarionBackend.repositories.AccessLogRepo;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

import static org.springframework.http.HttpStatus.ACCEPTED;

@ControllerAdvice
@RequiredArgsConstructor
public class AuditLoggingAdvice implements ResponseBodyAdvice<Object>, RequestBodyAdvice {
    private final AccessLogRepo accessLogRepo;
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object responseBody,
                                  @NotNull MethodParameter methodParameter,
                                  @NotNull MediaType mediaType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> aClass,
                                  @NotNull ServerHttpRequest serverHttpRequest,
                                  @NotNull ServerHttpResponse serverHttpResponse) {
        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            int requestStatus = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse().getStatus();
            HttpMethod method = serverHttpRequest.getMethod();
            if (requestStatus != ACCEPTED.value()) {
                AccessLogItemEntity entity = new AccessLogItemEntity();
                entity.setEndpoint(String.valueOf(serverHttpRequest.getURI()));
                entity.setIncomingIp(String.valueOf(serverHttpRequest.getRemoteAddress()));
                entity.setHttpResponseCode(requestStatus);
                entity.setMethod(method.name());
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = "ANONYMOUS";
                if (principal instanceof CustomUserDetails) {
                    username = ((CustomUserDetails) principal).getUsername();
                }
                entity.setMail(username);

                accessLogRepo.saveAndFlush(entity);
            }
        }

        return responseBody;
    }
}
