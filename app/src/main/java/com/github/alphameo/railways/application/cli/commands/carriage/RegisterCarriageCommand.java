package com.github.alphameo.railways.application.cli.commands.carriage;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class RegisterCarriageCommand implements CliCommand {

    private static String NAME = "register";
    public static String SHORT_NAME = "a";
    public static String ARGS_TEMPLATE = "<number> [type] [capacity]";
    public final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
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
    public void execute() {
        final int argsMinCount = 1;
        final int argsMaxCount = 3;
        if (args.length < argsMinCount || args.length > argsMaxCount) {
            throw new CliArgsCountException(String.format("inside [%n;%n]", argsMinCount, argsMaxCount));
        }
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
