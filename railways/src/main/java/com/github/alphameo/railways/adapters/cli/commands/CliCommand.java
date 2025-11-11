package com.github.alphameo.railways.adapters.cli.commands;

public interface CliCommand {

    void execute();

    void setArgs(String[] args);

    String getSignature();

    String getName();

    String getShortName();
}
