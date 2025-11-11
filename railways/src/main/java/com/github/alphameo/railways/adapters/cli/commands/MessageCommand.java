package com.github.alphameo.railways.adapters.cli.commands;


import com.github.alphameo.railways.adapters.cli.Renderer;

import lombok.Getter;
import lombok.Setter;

public class MessageCommand implements CliCommand {

    private final String name;
    private final String shortName;
    @Setter
    @Getter
    private String message;

    public MessageCommand(final String name, final String shortName, final String msg) {
        this.name = name;
        this.shortName = shortName;
        this.message = msg;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public String getSignature() {
        return Renderer.renderSignature(name, shortName, "");
    }

    public void setArgs(final String[] args) {
        if (args.length != 0) {
            this.message = args[0];
        }
    }

    @Override
    public void execute() {
        System.out.println(this.message);
    }
}
