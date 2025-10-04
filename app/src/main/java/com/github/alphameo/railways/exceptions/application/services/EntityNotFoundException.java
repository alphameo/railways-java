package com.github.alphameo.railways.exceptions.application.services;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
