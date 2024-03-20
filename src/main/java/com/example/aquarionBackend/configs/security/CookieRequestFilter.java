package com.example.aquarionBackend.configs.security;

import com.example.aquarionBackend.exceptions.WrongTokenExc;
import com.example.aquarionBackend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CookieRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private String COOKIE_NAME = "session_token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        try {
            String mail = jwtUtils.getEmailFromToken(cookie != null ? cookie.getValue() : null);
            if (mail != null) {
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_AUTHORIZED"));
                UserDetails userDetails = new CustomUserDetails(mail, authorities);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (WrongTokenExc e) {

        }
        filterChain.doFilter(request, response);
    }
}
