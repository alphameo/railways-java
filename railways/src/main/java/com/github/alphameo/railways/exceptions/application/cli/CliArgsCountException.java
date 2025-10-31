package com.github.alphameo.railways.exceptions.application.cli;

import com.github.alphameo.railways.exceptions.application.ApplicationException;

public class CliArgsCountException extends ApplicationException {

    private static final String MSG_FMT = "args count should be %s";

    public CliArgsCountException(final String rule) {
        super(String.format(MSG_FMT, rule));
    }

    public CliArgsCountException(final String rule, final Throwable cause) {
        super(String.format(MSG_FMT, rule), cause);
    }

}
