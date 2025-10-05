package com.github.alphameo.railways.exceptions.application.services;

public class EntityAlreadyExistsException extends ServiceException {

    private static final String MSG_FMT = "Entity %s with id=%s already exists";

    public EntityAlreadyExistsException(final String entity, final Object id) {
        super(String.format(MSG_FMT, entity, id.toString()));
    }
}
