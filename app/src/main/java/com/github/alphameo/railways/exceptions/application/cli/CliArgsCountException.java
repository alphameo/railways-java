package com.github.alphameo.railways.exceptions.application.cli;

import com.github.alphameo.railways.exceptions.application.ApplicationException;

public class CliArgsCountException extends ApplicationException {

    private static final String MSG_FMT = "args count != %n";
    public CliArgsCountException(int count) {
        super(String.format(MSG_FMT, count));
    }

    public CliArgsCountException(int count, Throwable cause) {
        super(String.format(MSG_FMT, count), cause);
    }

}
