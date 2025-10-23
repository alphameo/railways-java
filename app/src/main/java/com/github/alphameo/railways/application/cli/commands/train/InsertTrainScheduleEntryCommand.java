package com.github.alphameo.railways.application.cli.commands.train;

import java.time.LocalDateTime;

import com.github.alphameo.railways.application.cli.DateParser;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;
import lombok.Setter;

public class InsertTrainScheduleEntryCommand implements CliCommand {

    private static String NAME = "insertScheduleEntry";
    private final String SIGNATURE = String
            .format("%s <train_id> <orderIndex> <station_id> [<arrival_time> [<departure_time>]]", NAME);
    @Setter
    private String[] args;
    private TrainService service;

    public InsertTrainScheduleEntryCommand(@NonNull final TrainService service) {
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
        final int argsMinCount = 3;
        if (args.length >= argsMinCount) {
            throw new CliArgsCountException("== " + argsMinCount);
        }

        final var id = Long.parseLong(args[0]);
        final var orderIndex = Integer.parseInt(args[1]);
        final var stationId = Long.parseLong(args[2]);
        final LocalDateTime arrivalTime;
        final LocalDateTime departureTime;
        if (args.length >= 4) {
            arrivalTime = DateParser.parse(args[3]);
        } else {
            arrivalTime = null;
        }
        if (args.length >= 5) {
            departureTime = DateParser.parse(args[4]);
        } else {
            departureTime = null;
        }
        final var scheduleEntry = new ScheduleEntryDto(stationId, arrivalTime, departureTime);
        this.service.insertScheduleEntry(id, scheduleEntry, orderIndex);
    }
}
