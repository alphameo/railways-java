package com.github.alphameo.railways.adapters.cli.commands.locomotive;

import com.github.alphameo.railways.adapters.cli.Renderer;
import com.github.alphameo.railways.adapters.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.adapters.cli.CliArgsCountException;

import lombok.NonNull;

public class ListLocomotivesCommand implements CliCommand {

    public static final String NAME = "list";
    public static final String SHORT_NAME = "l";
    public static final String ARGS_TEMPLATE = "";
    public static final int ARGS_COUNT = 0;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    private LocomotiveService service;

    public ListLocomotivesCommand(@NonNull final LocomotiveService service) {
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
    }

    @Override
    public void execute() {
        final var list = this.service.listAllLocomotives();
        System.out.println(Renderer.renderList("All Locomotives", list));
    }
}
