package com.example.aquarionBackend.exceptions;

import lombok.*;

public class CantSendMailExc extends GlobalAppException{
    public CantSendMailExc() {
        super(503, "Service unavailable");
    }
}
