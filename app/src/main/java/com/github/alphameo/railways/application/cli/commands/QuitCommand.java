package com.github.alphameo.railways.application.cli.commands;

import com.github.alphameo.railways.application.cli.Renderer;

import lombok.NonNull;

public class QuitCommand implements CliCommand {

    public static final String NAME = "quit";
    public static final String SHORT_NAME = "q";
    public static final String ARGS_TEMPLATE = "";
    public static final String SIGNATURE = Renderer.renderSignature(NAME, SHORT_NAME, ARGS_TEMPLATE);

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
    }

    @Override
    public void execute() {
        System.out.println("Application closed.");
        System.exit(0);
    }
}
