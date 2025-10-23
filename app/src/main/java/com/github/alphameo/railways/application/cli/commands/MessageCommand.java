package com.github.alphameo.railways.application.cli.commands;

public class MessageCommand implements CliCommand {

    private final String name;
    private final String shortName;
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
        return this.getName();
    }

    public void setArgs(final String[] args) {
        this.msg = args[0];
    }

    @Override
    public void execute() {
        System.out.println(this.msg);
    }
}
