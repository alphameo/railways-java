package com.github.alphameo.railways.application.cli.commands.line;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;

public class FindLineByIdCommand implements CliCommand {

    public static final String NAME = "findById";
    public static final String SHORT_NAME = "f";
    public static final String ARGS_TEMPLATE = "<id>";
    public static final int ARGS_COUNT = 1;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private String[] args;
    private LineService service;

    public FindLineByIdCommand(@NonNull final LineService service) {
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
        final var id = Id.fromString(args[0]);
        final var entity = this.service.findById(id);
        System.out.println(entity);
    }
}
