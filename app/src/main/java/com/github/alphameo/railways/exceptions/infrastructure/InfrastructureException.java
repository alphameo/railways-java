package com.github.alphameo.railways.exceptions.infrastructure;

import com.github.alphameo.railways.exceptions.RailwaysException;

public class InfrastructureException extends RailwaysException {

    public InfrastructureException(String msg) {
        super(msg);
    }

    public InfrastructureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
