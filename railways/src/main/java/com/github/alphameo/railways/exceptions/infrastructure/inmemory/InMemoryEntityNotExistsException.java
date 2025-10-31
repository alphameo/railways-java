package com.github.alphameo.railways.exceptions.infrastructure.inmemory;

public class InMemoryEntityNotExistsException extends InMemoryException {

    private static final String MSG_FMT = "Entity %s with id=%s does not exists";

    public InMemoryEntityNotExistsException(final String entity, final Object id) {
        super(String.format(MSG_FMT, entity, id.toString()));
    }
}
