package com.github.alphameo.railways.application.cli.commands.line;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;
import lombok.Setter;

public class ListLineStationsCommand implements CliCommand {

    private static String NAME = "stations";
    private final String SIGNATURE = String.format("%s <line_id>", NAME);
    @Setter
    private String[] args;
    private LineService service;

    public ListLineStationsCommand(@NonNull final LineService service) {
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
        final int argsCount = 1;
        if (args.length != argsCount) {
            throw new CliArgsCountException("== " + argsCount);
        }
        final var id = Long.parseLong(args[0]);
        final var list = this.service.listLineStations(id);
        final var out = Renderer.renderList(
                String.format(
                        "Station order list for line (id=%s)",
                        id),
                list);
        System.out.println(out);
    }

}
