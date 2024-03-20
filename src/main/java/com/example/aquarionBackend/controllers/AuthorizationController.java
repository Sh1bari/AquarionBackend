package com.example.aquarionBackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("")
@Tag(name = "Authorization API", description = "")
public class AuthorizationController {
    @PostMapping("/setCookie")
    public String setCookie(@RequestParam(name = "token") String token, HttpServletResponse response) {
        // Создаем объект куки с именем "session_token" и устанавливаем значение из входной строки
        Cookie cookie = new Cookie("session_token", token);
        cookie.setPath("/");
        // Устанавливаем срок жизни куки (по вашему выбору)
        cookie.setMaxAge(3600); // Например, 1 час
        // Добавляем куки в ответ
        response.addCookie(cookie);
        return "Cookie успешно установлен";
    }
}
