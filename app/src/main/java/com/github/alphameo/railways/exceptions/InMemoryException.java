package com.github.alphameo.railways.exceptions;

import com.github.alphameo.railways.exceptions.repository.RepositoryException;

public class InMemoryException extends RepositoryException {

    public InMemoryException(String msg) {
        super(msg);
    }
}
