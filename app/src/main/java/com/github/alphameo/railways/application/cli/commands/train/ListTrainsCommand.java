package com.github.alphameo.railways.application.cli.commands.train;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class ListTrainsCommand implements CliCommand {

    private static String NAME = "list";
    public static String SHORT_NAME = "l";
    public static String ARGS_TEMPLATE = "";
    public final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
    private String[] args;
    private TrainService service;

    public ListTrainsCommand(@NonNull final TrainService service) {
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
        final int argsCount = 0;
        if (args.length != argsCount) {
            throw new CliArgsCountException("== " + argsCount);
        }
        final var list = this.service.listAll();
        System.out.println(Renderer.renderList("All Trains", list));
    }
}
