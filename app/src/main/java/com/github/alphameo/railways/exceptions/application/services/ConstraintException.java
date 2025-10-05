package com.github.alphameo.railways.exceptions.application.services;

public class ConstraintException extends ServiceException {

    private static final String MSG_FMT = "Constraint of entity %s broken: %s";

    public ConstraintException(final String entity, final String constraint) {
        super(String.format(MSG_FMT, entity, constraint));
    }
}
