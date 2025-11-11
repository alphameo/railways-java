package com.github.alphameo.railways.exceptions.adapters.cli;

import com.github.alphameo.railways.exceptions.adapters.AdapterException;

public class CliException extends AdapterException {

    public CliException(String msg) {
        super(msg);
    }

    public CliException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
