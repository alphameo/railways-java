package com.github.alphameo.railways.application.cli.commands.carriage;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;

public class RegisterCarriageCommand implements CliCommand {

    public static final String NAME = "register";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<number> [type] [capacity]";
    public static final int ARGS_MIN_COUNT = 1;
    public static final int ARGS_MAX_COUNT = 3;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private CarriageService service;

    public RegisterCarriageCommand(@NonNull final CarriageService service) {
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
        if (args.length < ARGS_MIN_COUNT || args.length > ARGS_MAX_COUNT) {
            throw new CliArgsCountException(String.format("inside [%n;%n]", ARGS_MIN_COUNT, ARGS_MAX_COUNT));
        }
        this.args = args;
    }

    @Override
    public void execute() {
        final var number = args[0];
        final String contentType;
        if (args.length >= 2) {
            contentType = args[1];
        } else {
            contentType = null;
        }
        final Long capacity;
        if (args.length >= 3) {
            capacity = Long.parseLong(args[2]);
        } else {
            capacity = null;
        }
        final var dto = new CarriageDto(null, number, contentType, capacity);
        this.service.register(dto);
    }
}
