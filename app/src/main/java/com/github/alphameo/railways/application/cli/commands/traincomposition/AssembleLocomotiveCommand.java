package com.github.alphameo.railways.application.cli.commands.traincomposition;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class AssembleLocomotiveCommand implements CliCommand {

    public static final String NAME = "assemble";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<locomotiveId> <carriageId> [carriageId...]";
    public static final int ARGS_MIN_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    @Setter
    private String[] args;
    private TrainCompositionService service;

    public AssembleLocomotiveCommand(@NonNull final TrainCompositionService service) {
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
        if (args.length < ARGS_MIN_COUNT) {
            throw new CliArgsCountException(">= " + ARGS_MIN_COUNT);
        }
        final var locomotiveId = Long.parseLong(args[0]);
        final List<Long> carriageIds = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            carriageIds.add(Long.parseLong(args[i]));
        }
        final var dto = new TrainCompositionDto(null, locomotiveId, carriageIds);
        this.service.assembleTrainComposition(dto);
    }
}
