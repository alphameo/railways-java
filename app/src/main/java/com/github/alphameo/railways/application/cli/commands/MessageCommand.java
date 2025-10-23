package com.github.alphameo.railways.application.cli.commands;

public class MessageCommand implements CliCommand {

    private final String name;
    private String msg;

    public MessageCommand(final String name, final String msg) {
        this.name = name;
        this.msg = msg;
    }

    @Override
    public String getName() {
        return name;
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
