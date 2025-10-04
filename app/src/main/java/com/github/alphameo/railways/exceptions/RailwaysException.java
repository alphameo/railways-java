package com.github.alphameo.railways.exceptions;

public class RailwaysException extends RuntimeException {

    public RailwaysException(String msg) {
        super(msg);
    }

    public RailwaysException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
