package com.example.aquarionBackend.exceptions;

import lombok.*;

public class ServiceUnavailableExc extends GlobalAppException{
    public ServiceUnavailableExc() {
        super(503, "Service unavailable");
    }
}
