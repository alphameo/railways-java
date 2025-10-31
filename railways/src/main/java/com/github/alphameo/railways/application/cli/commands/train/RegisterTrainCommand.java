package com.github.alphameo.railways.application.cli.commands.train;

import java.util.ArrayList;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;

public class RegisterTrainCommand implements CliCommand {

    public static final String NAME = "register";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<number> <trainCompositionId>";
    public static final int ARGS_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

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
    public void setArgs(@NonNull final String[] args) {
        if (args.length != ARGS_COUNT) {
            throw new CliArgsCountException("== " + ARGS_COUNT);
        }
        this.args = args;
    }

    @Override
    public void execute() {
        final var number = args[0];
        final var trainCompositionId = Id.fromString(args[0]);
        final var schedule = new ArrayList<ScheduleEntryDto>();
        final var dto = new TrainDto(null, number, trainCompositionId, schedule);
        this.service.register(dto);
    }
}
