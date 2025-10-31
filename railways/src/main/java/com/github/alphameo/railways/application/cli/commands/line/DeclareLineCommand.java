package com.github.alphameo.railways.application.cli.commands.line;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;

public class DeclareLineCommand implements CliCommand {

    public static final String NAME = "declare";
    public static final String SHORT_NAME = "a";
    public static final String ARGS_TEMPLATE = "<name> <stationId> [stationId...]";
    public static final int ARGS_MIN_COUNT = 2;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private LineService service;

    public DeclareLineCommand(@NonNull final LineService service) {
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
        final var name = args[0];
        final List<Id> stationIds = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            stationIds.add(Id.fromString(args[0]));
        }
        final var dto = new LineDto(null, name, stationIds);
        this.service.declareLine(dto);
    }
}
