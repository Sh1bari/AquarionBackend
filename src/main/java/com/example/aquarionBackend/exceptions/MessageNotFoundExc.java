package com.example.aquarionBackend.exceptions;

import lombok.*;

public class MessageNotFoundExc extends GlobalAppException{
    public MessageNotFoundExc() {
        super(404, "Message not found");
    }
}
