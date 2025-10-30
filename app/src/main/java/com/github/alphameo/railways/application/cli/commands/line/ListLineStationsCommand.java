package com.github.alphameo.railways.application.cli.commands.line;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.NonNull;

public class ListLineStationsCommand implements CliCommand {

    public static final String NAME = "stations";
    public static final String SHORT_NAME = "lst";
    public static final String ARGS_TEMPLATE = "<line_id>";
    public static final int ARGS_COUNT = 1;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

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
