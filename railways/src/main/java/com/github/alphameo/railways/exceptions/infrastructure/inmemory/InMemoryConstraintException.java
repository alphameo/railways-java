package com.github.alphameo.railways.exceptions.infrastructure.inmemory;

public class InMemoryConstraintException extends InMemoryException {

    private static final String MSG_FMT = "Constraint of %s: %s";

    public InMemoryConstraintException(String msg) {
        super(msg);
    }

    public InMemoryConstraintException(String param, String constraint) {
        super(String.format(MSG_FMT, param, constraint));
    }

    public InMemoryConstraintException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
