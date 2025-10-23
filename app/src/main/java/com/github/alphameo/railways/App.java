package com.github.alphameo.railways;

import com.github.alphameo.railways.application.cli.CliApp;
import com.github.alphameo.railways.application.cli.CliModule;
import com.github.alphameo.railways.application.cli.commands.carriage.FindCarriageByIdCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.FindCarriageByNumberCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.ListCarriagesCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.RegisterCarriageCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.UnregisterCarriageCommand;
import com.github.alphameo.railways.application.cli.commands.line.DeclareLineCommand;
import com.github.alphameo.railways.application.cli.commands.line.DisbandLineCommand;
import com.github.alphameo.railways.application.cli.commands.line.FindLineByIdCommand;
import com.github.alphameo.railways.application.cli.commands.line.ListLineStationsCommand;
import com.github.alphameo.railways.application.cli.commands.line.ListLinesCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.FindLocomotiveByIdCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.ListLocomotivesCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.RegisterLocomotiveCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.UnregisterLocomotiveCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.infrastructure.inmemory.InMemoryStorage;

public class App {
    public static void main(String[] args) {
        final var inMemStorage = InMemoryStorage.getInstance();
        final var carServ = new CarriageService(inMemStorage.getCarrRepo());
        final var lineServ = new LineService(inMemStorage.getLineRepo(), inMemStorage.getStationRepo());
        final var locoServ = new LocomotiveService(inMemStorage.getLocoRepo());
        final var cliApp = new CliApp();

        final var carrMod = new CliModule("carriage");
        carrMod.addCmd(new RegisterCarriageCommand(carServ));
        carrMod.addCmd(new ListCarriagesCommand(carServ));
        carrMod.addCmd(new FindCarriageByIdCommand(carServ));
        carrMod.addCmd(new FindCarriageByNumberCommand(carServ));
        carrMod.addCmd(new UnregisterCarriageCommand(carServ));

        final var lineMod = new CliModule("line");
        lineMod.addCmd(new DeclareLineCommand(lineServ));
        lineMod.addCmd(new FindLineByIdCommand(lineServ));
        lineMod.addCmd(new ListLinesCommand(lineServ));
        lineMod.addCmd(new DisbandLineCommand(lineServ));
        lineMod.addCmd(new ListLineStationsCommand(lineServ));

        final var locoMod = new CliModule("locomotive");
        locoMod.addCmd(new RegisterLocomotiveCommand(locoServ));
        locoMod.addCmd(new ListLocomotivesCommand(locoServ));
        locoMod.addCmd(new FindLocomotiveByIdCommand(locoServ));
        locoMod.addCmd(new UnregisterLocomotiveCommand(locoServ));

        cliApp.addModule(carrMod);
        cliApp.addModule(lineMod);
        cliApp.addModule(locoMod);
        cliApp.run();
    }
}
