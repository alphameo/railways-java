package com.github.alphameo.railways.adapters.cli.commands.locomotive;

import com.github.alphameo.railways.adapters.cli.Renderer;
import com.github.alphameo.railways.adapters.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.adapters.cli.CliArgsCountException;

import lombok.NonNull;

public class FindLocomotiveByNumberCommand implements CliCommand {

    public static final String NAME = "findByNumber";
    public static final String SHORT_NAME = "fnum";
    public static final String ARGS_TEMPLATE = "<number>";
    public static final int ARGS_COUNT = 1;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private LocomotiveService service;

    public FindLocomotiveByNumberCommand(@NonNull final LocomotiveService service) {
        this.service = service;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getShortName() {
        return SHORT_NAME;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }

    @Override
    public void setArgs(@NonNull final String[] args) {
        if (args.length != ARGS_COUNT) {
            throw new CliArgsCountException("== " + ARGS_COUNT);
        }
        this.args = args;
    }

    @Override
    public void execute() {
        final var number = args[0];
        final var entity = this.service.findLocomotiveByNumber(number);
        System.out.println(entity);
    }
}
