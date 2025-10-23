package com.github.alphameo.railways.application.cli.commands.locomotive;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class ListLocomotivesCommand implements CliCommand {

    private static String NAME = "list";
    public static String SHORT_NAME = "l";
    public static String ARGS_TEMPLATE = "";
    public final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
    private String[] args;
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
    public void execute() {
        final int argsCount = 0;
        if (args.length != argsCount) {
            throw new CliArgsCountException("== " + argsCount);
        }
        final var list = this.service.listAll();
        System.out.println(Renderer.renderList("All Locomotives", list));
    }
}
