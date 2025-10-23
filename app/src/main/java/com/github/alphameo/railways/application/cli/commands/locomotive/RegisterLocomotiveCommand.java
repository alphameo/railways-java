package com.github.alphameo.railways.application.cli.commands.locomotive;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class RegisterLocomotiveCommand implements CliCommand {

    private static String NAME = "register";
    public static String SHORT_NAME = "a";
    public static String ARGS_TEMPLATE = "<number> <model>";
    public final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
    private String[] args;
    private LocomotiveService service;

    public RegisterLocomotiveCommand(@NonNull final LocomotiveService service) {
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
        final int argsCount = 2;
        if (args.length != argsCount) {
            throw new CliArgsCountException("==  " + argsCount);
        }
        final var number = args[0];
        final var model = args[1];
        final var dto = new LocomotiveDto(null, number, model);
        this.service.register(dto);
    }
}
