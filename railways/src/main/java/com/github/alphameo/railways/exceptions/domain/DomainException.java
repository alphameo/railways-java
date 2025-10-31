package com.github.alphameo.railways.exceptions.domain;

import com.github.alphameo.railways.exceptions.RailwaysException;

public class DomainException extends RailwaysException {

    public DomainException(String msg) {
        super(msg);
    }

    public DomainException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
