package com.github.alphameo.railways.application.cli.commands.station;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;

public class RegisterStationCommand implements CliCommand {

    public static final String NAME = "register";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<number> <model>";
    public static final int ARGS_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private StationService service;

    public RegisterStationCommand(@NonNull final StationService service) {
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
        final var model = args[1];
        final var dto = new StationDto(null, number, model);
        this.service.register(dto);
    }
}
