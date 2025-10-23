package com.github.alphameo.railways;

import com.github.alphameo.railways.application.cli.CliApp;
import com.github.alphameo.railways.application.cli.CliModule;
import com.github.alphameo.railways.application.cli.commands.carriage.ListCarriagesCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.RegisterCarriageCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.infrastructure.inmemory.InMemoryStorage;

public class App {
    public static void main(String[] args) {
        final var inMemStorage = InMemoryStorage.getInstance();
        CarriageService carServ = new CarriageService(inMemStorage.getCarrRepo());
        final var cliApp = new CliApp();
        final var carrMod = new CliModule("carriage");
        carrMod.addCmd(new RegisterCarriageCommand(carServ));
        carrMod.addCmd(new ListCarriagesCommand(carServ));
        cliApp.addModule(carrMod);
        cliApp.run();
    }
}
