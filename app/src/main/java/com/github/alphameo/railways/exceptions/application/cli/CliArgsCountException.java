package com.github.alphameo.railways.exceptions.application.cli;

import com.github.alphameo.railways.exceptions.application.ApplicationException;

public class CliArgsCountException extends ApplicationException {

    private static final String MSG_FMT = "args count %s %n";

    public CliArgsCountException(String rule, int count) {
        super(String.format(MSG_FMT, rule, count));
    }

    public CliArgsCountException(String rule, int count, Throwable cause) {
        super(String.format(MSG_FMT, count), cause);
    }

}
