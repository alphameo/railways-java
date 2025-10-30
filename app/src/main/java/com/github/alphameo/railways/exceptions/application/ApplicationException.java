package com.github.alphameo.railways.exceptions.application;

import com.github.alphameo.railways.exceptions.RailwaysException;

public class ApplicationException extends RailwaysException {

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
