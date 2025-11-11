package com.github.alphameo.railways.adapters.cli;

import java.util.Scanner;

import com.github.alphameo.railways.application.services.ServiceProvider;

public class CliApp {

    private final CliCommandManager cliManager;

    public CliApp(ServiceProvider serviceProvider) {
        cliManager = new CliCommandManager(serviceProvider);
    }

    public void run() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print(Renderer.PROMPT);
        while (scanner.hasNextLine()) {
            try {
                final var line = scanner.nextLine();
                final String[] tokens = line.strip().split("\\s+");
                cliManager.dispatch(tokens);
                System.out.println();
                System.out.print(Renderer.PROMPT);
            } catch (final Exception e) {
                System.out.printf("Error: %s\n", e.getMessage());
            }
        }
        scanner.close();
    }
}
