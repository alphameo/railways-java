package com.github.alphameo.railways.application.cli.commands.locomotive;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class RegisterLocomotiveCommand implements CliCommand {

    private static String NAME = "register";
    private final String SIGNATURE = String.format("%s <number> <model>", NAME);
    @Setter
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
    public String getSignature() {
        return SIGNATURE;
    }

    @Override
    public void execute() {
        final int argsCount = 3;
        if (args.length != argsCount) {
            throw new CliArgsCountException("==", argsCount);
        }
        final var number = args[0];
        final var model = args[1];
        final var dto = new LocomotiveDto(null, number, model);
        this.service.register(dto);
    }
}
