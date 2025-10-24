
package com.github.alphameo.railways.application.cli.commands.line;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class FindLineByIdCommand implements CliCommand {

    public static final String NAME = "findById";
    public static final String SHORT_NAME = "f";
    public static final String ARGS_TEMPLATE = "<id>";
    public static final int ARGS_COUNT = 1;
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

    @Setter
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
    public void execute() {
        if (args.length != ARGS_COUNT) {
            throw new CliArgsCountException("== " + ARGS_COUNT);
        }
        final var id = Long.parseLong(args[0]);
        final var entity = this.service.findById(id);
        System.out.println(entity);
    }
}
