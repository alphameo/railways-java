package com.github.alphameo.railways.application.cli.commands.carriage;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.exceptions.application.cli.CliException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class RegisterCarriageCommand implements CliCommand {

    private static String NAME = "create";
    private final String SIGNATURE = String.format("%s <number> <type> <capacity>", NAME);
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
    public String getSignature() {
        return SIGNATURE;
    }

    @Override
    public void execute() {
        final int argsCount = 3;
        if (args.length != argsCount) {
            throw new CliException("Error: args count != " + argsCount);
        }
        final var number = args[0];
        final var contentType = args[1];
        final var capacity = Long.parseLong(args[2]);
        final var carriageDto = new CarriageDto(number, contentType, capacity);
        this.service.register(carriageDto);
    }
}
