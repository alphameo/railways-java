package com.github.alphameo.railways.exceptions.infrastructure.inmemory;

import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class InMemoryException extends InfrastructureException {

    public InMemoryException(String msg) {
        super(msg);
    }

    public InMemoryException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
