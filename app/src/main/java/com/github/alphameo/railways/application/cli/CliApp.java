package com.github.alphameo.railways.application.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.github.alphameo.railways.application.cli.commands.CliCommand;

import lombok.NonNull;

public class CliApp {

    private final List<CliModule> modules = new ArrayList<>();

    public void addModule(@NonNull final CliModule module) {
        this.modules.add(module);
    }

    public void run() {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("> ");
                final var inp = scanner.nextLine();
                final String[] args = inp.split(" ");
                final var modName = args[0];
                if (modName.equals("help")) {
                    System.out.print(getHelp());
                    continue;
                }
                if (modName.equals("quit")) {
                    break;
                }
                if (modName.equals("")) {
                    continue;
                }

                String cmdName;
                final String[] cmdArgs;
                if (args.length > 1) {
                    cmdName = args[1];
                    cmdArgs = Arrays.copyOfRange(args, 2, args.length);
                } else {
                    cmdName = "";
                    cmdArgs = new String[0];
                }
                CliCommand cmd;
                var isFound = false;
                for (final var module : modules) {
                    if (module.getName().equals(modName)) {
                        cmd = module.getCmd(cmdName, cmdArgs);
                        try {
                            cmd.execute();
                        } catch (final Exception e) {
                            System.out.printf("Execution error: %s\n%s\n", e.getMessage(), cmd.getSignature());
                        }
                        isFound = true;
                        continue;
                    }
                }
                if (!isFound) {
                    System.out.printf("No such module: %s\n%s\n", modName, getHelp());
                }
            } catch (final Exception e) {
                System.out.println("Error: " + e.getCause());
            }
        }
        scanner.close();
    }

    public String getHelp() {
        final var sb = new StringBuilder();

        sb.append("Available modules:\n");

        for (final var module : modules) {
            sb.append("\t");
            sb.append(module.getName());
            sb.append("\n");
        }

        return sb.toString();
    }
}
