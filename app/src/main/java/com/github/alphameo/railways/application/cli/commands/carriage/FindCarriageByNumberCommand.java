package com.github.alphameo.railways.application.cli.commands.carriage;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class FindCarriageByNumberCommand implements CliCommand {

    private static String NAME = "findByNumber";
    private final String SIGNATURE = String.format("%s <number>", NAME);
    @Setter
    private String[] args;
    private CarriageService service;

    public FindCarriageByNumberCommand(@NonNull final CarriageService service) {
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
        final int argsCount = 1;
        if (args.length != argsCount) {
            throw new CliArgsCountException("== " + argsCount);
        }
        final var number = args[0];
        final var entity = this.service.findByNumber(number);
        System.out.println(entity);
    }
}
