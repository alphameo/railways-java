package com.github.alphameo.railways.exceptions.adapters;

import com.github.alphameo.railways.exceptions.RailwaysException;

public class AdapterException extends RailwaysException {

    public AdapterException(String msg) {
        super(msg);
    }

    public AdapterException(Throwable cause) {
        super(cause);
    }

    public AdapterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
