package com.store.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    private HttpStatus status;

    public RequestException(String message) {
        super();
        this.message = message;
    }

    public RequestException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
