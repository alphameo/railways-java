package com.github.alphameo.railways.application.cli.commands.train;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class FindTrainByNumberCommand implements CliCommand {

    private static String NAME = "findByNumber";
    private final String SIGNATURE = String.format("%s <number>", NAME);
    @Setter
    private String[] args;
    private TrainService service;

    public FindTrainByNumberCommand(@NonNull final TrainService service) {
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
