package com.github.alphameo.railways.application.cli.commands;

public interface CliCommand {

    void execute();

    void setArgs(String[] args);

    String getSignature();

    String getName();
    
    String getShortName();
}
