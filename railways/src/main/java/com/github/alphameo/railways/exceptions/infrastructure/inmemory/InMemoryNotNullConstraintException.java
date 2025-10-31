package com.github.alphameo.railways.exceptions.infrastructure.inmemory;

public class InMemoryNotNullConstraintException extends InMemoryConstraintException {

    private static final String MSG_FMT = "Parameter %s cannot be null";

    public InMemoryNotNullConstraintException(final String param) {
        super(String.format(MSG_FMT, param));
    }
}
