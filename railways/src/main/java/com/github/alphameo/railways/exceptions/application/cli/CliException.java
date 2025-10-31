package com.github.alphameo.railways.exceptions.application.cli;

import com.github.alphameo.railways.exceptions.application.ApplicationException;

public class CliException extends ApplicationException {

    public CliException(String msg) {
        super(msg);
    }

    public CliException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
