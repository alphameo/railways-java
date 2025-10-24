package com.github.alphameo.railways.application.cli.commands.train;

import java.util.ArrayList;

import com.github.alphameo.railways.application.cli.Renderer;
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

    public static final String NAME = "register";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<number> <trainCompositionId>";
    public static final int ARGS_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

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
    public String getShortName() {
        return SHORT_NAME;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }

    @Override
    public void execute() {
        if (args.length != ARGS_COUNT) {
            throw new CliArgsCountException("== " + ARGS_COUNT);
        }
        final var number = args[0];
        final var trainCompositionId = Long.parseLong(args[1]);
        final var schedule = new ArrayList<ScheduleEntryDto>();
        final var dto = new TrainDto(null, number, trainCompositionId, schedule);
        this.service.register(dto);
    }
}
