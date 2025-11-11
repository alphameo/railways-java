package com.github.alphameo.railways.exceptions.adapters.cli;

import com.github.alphameo.railways.exceptions.adapters.AdapterException;

public class CliArgsCountException extends AdapterException {

    private static final String MSG_FMT = "args count must be %s";

    public CliArgsCountException(final String rule) {
        super(String.format(MSG_FMT, rule));
    }

    public CliArgsCountException(final String rule, final Throwable cause) {
        super(String.format(MSG_FMT, rule), cause);
    }

}
