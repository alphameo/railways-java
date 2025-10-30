package com.github.alphameo.railways;

import com.github.alphameo.railways.application.cli.CliApp;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.infrastructure.inmemory.InMemoryStorage;

public class App {
    public static void main(String[] args) {
        final var inMemoryStorage = InMemoryStorage.getInstance();
        ServiceProvider serviceProvider = new ServiceProvider(inMemoryStorage);

        final var cliApp = new CliApp(serviceProvider);
        cliApp.run();
    }
}
