package com.github.alphameo.railways.adapters.cli.commands.carriage;

import com.github.alphameo.railways.adapters.cli.Renderer;
import com.github.alphameo.railways.adapters.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.exceptions.adapters.cli.CliArgsCountException;

import lombok.NonNull;

public class UnregisterCarriageCommand implements CliCommand {

    public static final String NAME = "unregister";
    public static final String SHORT_NAME = "d";
    public static final String ARGS_TEMPLATE = "<id>";
    public static final int ARGS_COUNT = 1;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private CarriageService service;

    public UnregisterCarriageCommand(@NonNull final CarriageService service) {
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
        final var id = args[0];
        this.service.unregisterCarriage(id);
    }
}
