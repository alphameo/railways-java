package com.github.alphameo.railways.application.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.cli.commands.MessageCommand;

import lombok.NonNull;

public class CliModule {

    private final String name;

    private final List<CliCommand> commands = new ArrayList<>();
    private final MessageCommand helpCmd = new MessageCommand("help", "h", "");

    public CliModule(@NonNull final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCmd(@NonNull final CliCommand cmd) {
        commands.add(cmd);
        updateHelpMsg();
    }

    public Optional<CliCommand> getCommand(@NonNull final String name) {
        for (final var command : commands) {
            if (command.getName().equals(name) ||
                    command.getShortName().equals(name)) {
                return Optional.of(command);
            }
        }
        if (name.equals(helpCmd.getName()) || name.equals(helpCmd.getShortName())) {
            return Optional.of(helpCmd);
        }
        return Optional.empty();
    }

    public String getHelpMessage() {
        return this.helpCmd.getMessage();
    }

    private void updateHelpMsg() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("Commands for %s:\n", this.getName()));

        for (final var cmdSign : commands) {
            sb.append("\t");
            sb.append(cmdSign.getSignature());
            sb.append("\n");
        }
        sb.append("\t");
        sb.append(helpCmd.getSignature());

        this.helpCmd.setMessage(sb.toString());
    }
}
