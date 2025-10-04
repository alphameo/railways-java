package com.github.alphameo.railways.exceptions.infrastructure;

public class InMemoryException extends InfrastructureException {

    public InMemoryException(String msg) {
        super(msg);
    }

    public InMemoryException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
