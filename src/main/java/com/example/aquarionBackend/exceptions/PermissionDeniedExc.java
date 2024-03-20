package com.example.aquarionBackend.exceptions;

import lombok.*;

import java.util.UUID;

public class PermissionDeniedExc extends GlobalAppException{
    public PermissionDeniedExc(UUID messageId) {
        super(403, String.format("Permission to %s denied", messageId));
    }
}
