package com.github.alphameo.railways.application.cli.commands.train;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;
import lombok.Setter;

public class RemoveTrainScheduleEntryCommand implements CliCommand {

    public static final String NAME = "removeScheduleEntry";
    public static final String SHORT_NAME = "dse";
    public static final String ARGS_TEMPLATE = "<train_id> <order_index>";
    public static final int ARGS_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    @Setter
    private String[] args;
    private TrainService service;

    public RemoveTrainScheduleEntry(@NonNull final TrainService service) {
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
        final var id = Long.parseLong(args[0]);
        final var orderIndex = Integer.parseInt(args[1]);
        this.service.removeScheduleEntry(id, orderIndex);
    }
}
