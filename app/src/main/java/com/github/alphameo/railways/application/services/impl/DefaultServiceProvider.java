package com.github.alphameo.railways.application.services.impl;

import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.infrastructure.Storage;

import lombok.Getter;

@Getter
public class DefaultServiceProvider implements ServiceProvider {

    private final CarriageService carriageService;
    private final LineService lineService;
    private final LocomotiveService locomotiveService;
    private final StationService stationService;
    private final TrainService trainService;
    private final TrainCompositionService trainCompositionService;

    public DefaultServiceProvider(Storage storageContainer) {
        this.carriageService = new DefaultCarriageService(storageContainer.getCarriageRepository());
        this.lineService = new DefaultLineService(storageContainer.getLineRepository(),
                storageContainer.getStationRepository());
        this.locomotiveService = new DefaultLocomotiveService(storageContainer.getLocomotiveRepository());
        this.stationService = new DefaultStationService(storageContainer.getStationRepository());
        this.trainService = new DefaultTrainService(
                storageContainer.getTrainRepository(),
                storageContainer.getTrainCompositionRepository());
        this.trainCompositionService = new DefaultTrainCompositionService(
                storageContainer.getTrainCompositionRepository(),
                storageContainer.getCarriageRepository(),
                storageContainer.getLocomotiveRepository());
    }
}
