package com.example.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ExternalException extends RuntimeException {
    private final String message;
    private final HttpStatusCode httpStatusCode;

    public ExternalException(String message, HttpStatusCode httpStatusCode) {
        super(message);
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
