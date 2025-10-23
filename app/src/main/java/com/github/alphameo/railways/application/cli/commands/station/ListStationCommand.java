package com.github.alphameo.railways.application.cli.commands.station;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class ListStationCommand implements CliCommand {

    private static String NAME = "list";
    private final String SIGNATURE = String.format("%s", NAME);
    @Setter
    private String[] args;
    private StationService service;

    public ListStationCommand(@NonNull final StationService service) {
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
        final int argsCount = 0;
        if (args.length != argsCount) {
            throw new CliArgsCountException("== " + argsCount);
        }
        final var list = this.service.listAll();
        System.out.println(Renderer.renderList("All Stations", list));
    }
}
