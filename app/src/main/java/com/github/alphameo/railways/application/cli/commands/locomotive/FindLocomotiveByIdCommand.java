package com.github.alphameo.railways.application.cli.commands.locomotive;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class FindLocomotiveByIdCommand implements CliCommand {

    private static String NAME = "findById";
    public static String SHORT_NAME = "f";
    public static String ARGS_TEMPLATE = "<id>";
    public final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
    private String[] args;
    private LocomotiveService service;

    public FindLocomotiveByIdCommand(@NonNull final LocomotiveService service) {
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
        final int argsCount = 1;
        if (args.length != argsCount) {
            throw new CliArgsCountException("== " + argsCount);
        }
        final var id = Long.parseLong(args[0]);
        final var entity = this.service.findById(id);
        System.out.println(entity);
    }
}
