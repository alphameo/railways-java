package com.github.alphameo.railways.exceptions.application.services;

public class ConstraintException extends ServiceException {

    public ConstraintException(final String msg) {
        super(msg);
    }

    public ConstraintException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
