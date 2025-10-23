package com.github.alphameo.railways;

import com.github.alphameo.railways.application.cli.CliApp;
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
        final var lineServ = new LineService(inMemStorage.getLineRepo(), inMemStorage.getStationRepo());
        final var locoServ = new LocomotiveService(inMemStorage.getLocoRepo());
        final var stationServ = new StationService(inMemStorage.getStationRepo());
        final var trainServ = new TrainService(inMemStorage.getTrainRepo(), inMemStorage.getTrainCompoRepo());
        final var trainCompoServ = new TrainCompositionService(
            inMemStorage.getTrainCompoRepo(),
            inMemStorage.getCarrRepo(),
            inMemStorage.getLocoRepo()
        );

        final var cliApp = new CliApp(carServ, lineServ, locoServ, stationServ, trainServ, trainCompoServ);
        cliApp.run();
    }
}
