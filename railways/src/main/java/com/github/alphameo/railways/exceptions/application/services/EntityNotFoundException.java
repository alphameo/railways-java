package com.github.alphameo.railways.exceptions.application.services;

public class EntityNotFoundException extends ServiceException {

    private static final String MSG_FMT = "Entity %s with id=%s not found";

    public EntityNotFoundException(String entity, Object id) {
        super(String.format(MSG_FMT, entity, id.toString()));
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
