package com.github.alphameo.railways.exceptions.domain;

public class ValidationException extends DomainException {

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
