package com.github.alphameo.railways;

import com.github.alphameo.railways.application.cli.CliApp;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.infrastructure.inmemory.InMemoryStorage;

public class App {
    public static void main(String[] args) {
        final var inMemStorage = InMemoryStorage.getInstance();
        ServiceProvider servFact = new ServiceProvider(inMemStorage);

        final var cliApp = new CliApp(servFact);
        cliApp.run();
    }
}
