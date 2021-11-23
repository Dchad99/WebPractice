package com.store.exceptions;

public class LoginFailedException extends RuntimeException{

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
