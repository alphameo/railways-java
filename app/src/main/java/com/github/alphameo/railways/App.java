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
import com.github.alphameo.railways.application.cli.commands.station.FindStationByIdCommand;
import com.github.alphameo.railways.application.cli.commands.station.ListStationCommand;
import com.github.alphameo.railways.application.cli.commands.station.RegisterStationCommand;
import com.github.alphameo.railways.application.cli.commands.station.UnregisterStationCommand;
import com.github.alphameo.railways.application.cli.commands.train.FindTrainByIdCommand;
import com.github.alphameo.railways.application.cli.commands.train.ListTrainsCommand;
import com.github.alphameo.railways.application.cli.commands.train.RegisterTrainCommand;
import com.github.alphameo.railways.application.cli.commands.train.UnregisterTrainCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.AssembleLocomotiveCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.DisassembleTrainCompositionCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.FindTrainCompositionByIdCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.ListTrainCompositionsCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.infrastructure.inmemory.InMemoryStorage;

public class App {
    public static void main(String[] args) {
        final var inMemStorage = InMemoryStorage.getInstance();

        final var carServ = new CarriageService(inMemStorage.getCarrRepo());
        final var carrMod = new CliModule("carriage");
        carrMod.addCmd(new RegisterCarriageCommand(carServ));
        carrMod.addCmd(new ListCarriagesCommand(carServ));
        carrMod.addCmd(new FindCarriageByIdCommand(carServ));
        carrMod.addCmd(new FindCarriageByNumberCommand(carServ));
        carrMod.addCmd(new UnregisterCarriageCommand(carServ));

        final var lineServ = new LineService(inMemStorage.getLineRepo(), inMemStorage.getStationRepo());
        final var lineMod = new CliModule("line");
        lineMod.addCmd(new DeclareLineCommand(lineServ));
        lineMod.addCmd(new FindLineByIdCommand(lineServ));
        lineMod.addCmd(new ListLinesCommand(lineServ));
        lineMod.addCmd(new DisbandLineCommand(lineServ));
        lineMod.addCmd(new ListLineStationsCommand(lineServ));

        final var locoServ = new LocomotiveService(inMemStorage.getLocoRepo());
        final var locoMod = new CliModule("locomotive");
        locoMod.addCmd(new RegisterLocomotiveCommand(locoServ));
        locoMod.addCmd(new ListLocomotivesCommand(locoServ));
        locoMod.addCmd(new FindLocomotiveByIdCommand(locoServ));
        locoMod.addCmd(new UnregisterLocomotiveCommand(locoServ));

        final var stationServ = new StationService(inMemStorage.getStationRepo());
        final var stationMod = new CliModule("station");
        stationMod.addCmd(new RegisterStationCommand(stationServ));
        stationMod.addCmd(new ListStationCommand(stationServ));
        stationMod.addCmd(new FindStationByIdCommand(stationServ));
        stationMod.addCmd(new UnregisterStationCommand(stationServ));

        final var trainServ = new TrainService(inMemStorage.getTrainRepo(), inMemStorage.getTrainCompoRepo());
        final var trainMod = new CliModule("train");
        trainMod.addCmd(new RegisterTrainCommand(trainServ));
        trainMod.addCmd(new ListTrainsCommand(trainServ));
        trainMod.addCmd(new FindTrainByIdCommand(trainServ));
        trainMod.addCmd(new UnregisterTrainCommand(trainServ));

        final var trainCompoServ = new TrainCompositionService(
            inMemStorage.getTrainCompoRepo(),
            inMemStorage.getCarrRepo(),
            inMemStorage.getLocoRepo()
        );
        final var trainCompoMod = new CliModule("traincomposition");
        trainCompoMod.addCmd(new AssembleLocomotiveCommand(trainCompoServ));
        trainCompoMod.addCmd(new ListTrainCompositionsCommand(trainCompoServ));
        trainCompoMod.addCmd(new FindTrainCompositionByIdCommand(trainCompoServ));
        trainCompoMod.addCmd(new DisassembleTrainCompositionCommand(trainCompoServ));

        final var cliApp = new CliApp();
        cliApp.addModule(carrMod);
        cliApp.addModule(lineMod);
        cliApp.addModule(locoMod);
        cliApp.addModule(stationMod);
        cliApp.addModule(trainMod);
        cliApp.addModule(trainCompoMod);
        cliApp.run();
    }
}
