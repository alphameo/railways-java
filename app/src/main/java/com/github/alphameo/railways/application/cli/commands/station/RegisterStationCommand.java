package com.github.alphameo.railways.application.cli.commands.station;

import com.github.alphameo.railways.application.cli.Renderer;
import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.exceptions.application.cli.CliArgsCountException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class RegisterStationCommand implements CliCommand {

    private static String NAME = "register";
    public static String SHORT_NAME = "a";
    public static String ARGS_TEMPLATE = "<number> <model>";
    public final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);
    @Setter
    private String[] args;
    private StationService service;

    public RegisterStationCommand(@NonNull final StationService service) {
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
            throw new CliArgsCountException("== " + argsCount);
        }
        final var number = args[0];
        final var model = args[1];
        final var dto = new StationDto(null, number, model);
        this.service.register(dto);
    }
}
