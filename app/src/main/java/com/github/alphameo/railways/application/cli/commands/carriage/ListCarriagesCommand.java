package com.github.alphameo.railways.application.cli.commands.carriage;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class ListCarriagesCommand implements CliCommand {

    public static final String NAME = "list";
    public static final String SHORT_NAME = "l";
    public static final String ARGS_TEMPLATE = "";
    public static final int ARGS_COUNT = 0;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
    private String[] args;
    private CarriageService service;

    public ListCarriagesCommand(@NonNull final CarriageService service) {
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
        final var list = this.service.listAll();
        System.out.println(Renderer.renderList("All Carriages", list));
    }
}
