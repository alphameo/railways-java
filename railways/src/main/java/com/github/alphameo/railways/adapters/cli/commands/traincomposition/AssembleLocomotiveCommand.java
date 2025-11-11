package com.github.alphameo.railways.adapters.cli.commands.traincomposition;

import java.util.ArrayList;

import com.github.alphameo.railways.adapters.cli.Renderer;
import com.github.alphameo.railways.adapters.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.exceptions.adapters.cli.CliArgsCountException;

import lombok.NonNull;

public class AssembleLocomotiveCommand implements CliCommand {

    public static final String NAME = "assemble";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<locomotiveId> <carriageId> [carriageId...]";
    public static final int ARGS_MIN_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

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
    public void setArgs(@NonNull final String[] args) {
        if (args.length < ARGS_MIN_COUNT) {
            throw new CliArgsCountException(">= " + ARGS_MIN_COUNT);
        }
        this.args = args;
    }

    @Override
    public void execute() {
        final var locomotiveId = args[0];
        final var carriageIds = new ArrayList<String>();
        for (int i = 1; i < args.length; i++) {
            carriageIds.add(args[0]);
        }
        final var dto = new TrainCompositionDto(null, locomotiveId, carriageIds);
        this.service.assembleTrainComposition(dto);
    }
}
