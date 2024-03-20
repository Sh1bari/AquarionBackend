package com.example.aquarionBackend.configs.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String mail;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String mail, Collection<? extends GrantedAuthority> authorities) {
        this.mail = mail;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // You can return the actual password if needed
    }

    @Override
    public String getUsername() {
        return String.valueOf(mail);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
