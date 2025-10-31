package com.github.alphameo.railways.exceptions;

public class RailwaysException extends RuntimeException {

    public RailwaysException(String msg) {
        super(msg);
    }

    public RailwaysException(Throwable cause) {
        super(cause);
    }

    public RailwaysException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
