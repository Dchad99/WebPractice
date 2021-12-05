package com.store.ioc.exception;

public class BeanNotFoundException extends RuntimeException{
    public BeanNotFoundException() {
    }

    public BeanNotFoundException(String message) {
        super(message);
    }
}
