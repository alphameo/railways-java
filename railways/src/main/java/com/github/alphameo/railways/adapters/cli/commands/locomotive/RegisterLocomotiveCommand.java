package com.github.alphameo.railways.adapters.cli.commands.locomotive;

import com.github.alphameo.railways.adapters.cli.Renderer;
import com.github.alphameo.railways.adapters.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.adapters.cli.CliArgsCountException;

import lombok.NonNull;

public class RegisterLocomotiveCommand implements CliCommand {

    public static final String NAME = "register";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<number> <model>";
    public static final int ARGS_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private LocomotiveService service;

    public RegisterLocomotiveCommand(@NonNull final LocomotiveService service) {
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
            throw new CliArgsCountException("==  " + ARGS_COUNT);
        }
        this.args = args;
    }

    @Override
    public void execute() {
        final var number = args[0];
        final var model = args[1];
        final var dto = new LocomotiveDto(null, number, model);
        this.service.registerLocomotive(dto);
    }
}
