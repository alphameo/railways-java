package com.github.alphameo.railways.application.cli;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.cli.commands.MessageCommand;

import lombok.NonNull;

public class CliModule {

    private final String name;

    private final List<CliCommand> commands = new ArrayList<>();
    private final MessageCommand helpCmd = new MessageCommand("help", "h", "");
    private final MessageCommand noCmd = new MessageCommand("nocmd", "n", "");
    private String helpMsg = "";

    public CliModule(@NonNull final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCmd(@NonNull final CliCommand cmd) {
        commands.add(cmd);
        updateHelpMsg();
        helpCmd.setMsg(this.helpMsg);
        noCmd.setMsg(String.format("No such command: %s\n%s", name, this.helpMsg));
    }

    public CliCommand getCmd(@NonNull final String name, @NonNull final String[] args) {
        for (final var command : commands) {
            if (command.getName().equals(name) ||
                    command.getShortName().equals(name)) {
                command.setArgs(args);
                return command;
            }
        }
        if (name.equals("help") || name.equals("h")) {
            return helpCmd;
        }
        return noCmd;
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

        this.helpMsg = sb.toString();
    }
}
