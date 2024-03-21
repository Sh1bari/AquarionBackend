package com.example.aquarionBackend.exceptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Vladimir Krasnov
 */
@Data
@Slf4j
public abstract class GlobalAppException extends RuntimeException {
    protected int status;
    protected String message;
    protected Date timestamp;

    public GlobalAppException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
        log.warn(this.message);
    }
}
