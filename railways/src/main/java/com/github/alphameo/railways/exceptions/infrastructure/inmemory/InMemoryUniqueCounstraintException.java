package com.github.alphameo.railways.exceptions.infrastructure.inmemory;

public class InMemoryUniqueCounstraintException extends InMemoryConstraintException {

    private static final String MSG_FMT = "Parameter %s is not unique";

    public InMemoryUniqueCounstraintException(final String param) {
        super(String.format(MSG_FMT, param));
    }
}
