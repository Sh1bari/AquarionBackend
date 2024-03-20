package com.example.aquarionBackend.exceptions;

import lombok.*;

import java.util.UUID;

public class ChatSessionNotFoundExc extends GlobalAppException{
    public ChatSessionNotFoundExc(UUID id) {
        super(404, String.format("Chat session with id %s not found", id));
    }
}
