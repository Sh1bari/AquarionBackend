package com.example.aquarionBackend.utils;

import com.example.aquarionBackend.exceptions.WrongTokenExc;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwtSecret}")
    private String secret;

    public String getEmailFromToken(String token) {
        return (String) parseToken(token).get("email");
    }

    public Claims parseToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new WrongTokenExc();
        }
    }
}
