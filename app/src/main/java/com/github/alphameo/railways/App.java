package com.github.alphameo.railways;

import com.github.alphameo.railways.application.cli.CliApp;
import com.github.alphameo.railways.application.cli.CliModule;
import com.github.alphameo.railways.application.cli.commands.carriage.FindCarriageByIdCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.ListCarriagesCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.RegisterCarriageCommand;
import com.github.alphameo.railways.application.cli.commands.line.DeclareLineCommand;
import com.github.alphameo.railways.application.cli.commands.line.FindLineByIdCommand;
import com.github.alphameo.railways.application.cli.commands.line.ListLinesCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.infrastructure.inmemory.InMemoryStorage;

public class App {
    public static void main(String[] args) {
        final var inMemStorage = InMemoryStorage.getInstance();
        final var carServ = new CarriageService(inMemStorage.getCarrRepo());
        final var lineServ = new LineService(inMemStorage.getLineRepo(), inMemStorage.getStationRepo());
        final var cliApp = new CliApp();
        final var carrMod = new CliModule("carriage");
        carrMod.addCmd(new RegisterCarriageCommand(carServ));
        carrMod.addCmd(new ListCarriagesCommand(carServ));
        carrMod.addCmd(new FindCarriageByIdCommand(carServ));
        final var lineMod = new CliModule("line");
        lineMod.addCmd(new DeclareLineCommand(lineServ));
        lineMod.addCmd(new FindLineByIdCommand(lineServ));
        lineMod.addCmd(new ListLinesCommand(lineServ));
        cliApp.addModule(carrMod);
        cliApp.addModule(lineMod);
        cliApp.run();
    }
}
