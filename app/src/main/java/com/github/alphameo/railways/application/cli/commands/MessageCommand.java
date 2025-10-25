package com.github.alphameo.railways.application.cli.commands;

import com.github.alphameo.railways.application.cli.Renderer;

import lombok.Getter;
import lombok.Setter;

public class MessageCommand implements CliCommand {

    private final String name;
    private final String shortName;
    @Setter
    @Getter
    private String msg;

    public MessageCommand(final String name, final String shortName, final String msg) {
        this.name = name;
        this.shortName = shortName;
        this.msg = msg;
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
        this.msg = args[0];
    }

    @Override
    public void execute() {
        System.out.println(this.msg);
    }
}
