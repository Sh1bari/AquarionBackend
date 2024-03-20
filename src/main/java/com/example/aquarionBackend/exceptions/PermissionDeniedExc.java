package com.example.aquarionBackend.exceptions;

import lombok.*;

import java.util.UUID;

public class PermissionDeniedExc extends GlobalAppException{
    public PermissionDeniedExc() {
        super(403, "Permission denied");
    }
}
