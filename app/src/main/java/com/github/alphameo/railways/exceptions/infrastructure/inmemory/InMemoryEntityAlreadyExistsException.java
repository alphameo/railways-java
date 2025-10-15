package com.github.alphameo.railways.exceptions.infrastructure.inmemory;

public class InMemoryEntityAlreadyExistsException extends InMemoryException {

    private static final String MSG_FMT = "Entity %s with id=%s is already exists";

    public InMemoryEntityAlreadyExistsException(final String entity, final Object id) {
        super(String.format(MSG_FMT, entity, id.toString()));
    }
}
