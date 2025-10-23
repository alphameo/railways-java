package com.github.alphameo.railways.application.cli.commands.train;

import java.util.ArrayList;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class RegisterTrainCommand implements CliCommand {

    private static String NAME = "register";
    private final String SIGNATURE = String.format("%s <number> <trainCompositionId>", NAME);
    @Setter
    private String[] args;
    private TrainService service;

    public RegisterTrainCommand(@NonNull final TrainService service) {
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
        final int argsCount = 2;
        if (args.length != argsCount) {
            throw new CliArgsCountException("==", argsCount);
        }
        final var number = args[0];
        final var trainCompositionId = Long.parseLong(args[1]);
        final var schedule = new ArrayList<ScheduleEntryDto>();
        final var dto = new TrainDto(null, number, trainCompositionId, schedule);
        this.service.register(dto);
    }
}
