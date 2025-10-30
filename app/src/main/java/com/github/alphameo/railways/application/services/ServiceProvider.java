package com.github.alphameo.railways.application.services;

import com.github.alphameo.railways.infrastructure.Storage;

import lombok.Getter;

@Getter
public class ServiceProvider {

    private final CarriageService carriageService;
    private final LineService lineService;
    private final LocomotiveService locomotiveService;
    private final StationService stationService;
    private final TrainService trainService;
    private final TrainCompositionService trainCompositionService;

    public ServiceProvider(Storage storageContainer) {
        this.carriageService = new CarriageService(storageContainer.getCarriageRepository());
        this.lineService = new LineService(storageContainer.getLineRepository(),
                storageContainer.getStationRepository());
        this.locomotiveService = new LocomotiveService(storageContainer.getLocomotiveRepository());
        this.stationService = new StationService(storageContainer.getStationRepository());
        this.trainService = new TrainService(
                storageContainer.getTrainRepository(),
                storageContainer.getTrainCompositionRepository());
        this.trainCompositionService = new TrainCompositionService(
                storageContainer.getTrainCompositionRepository(),
                storageContainer.getCarriageRepository(),
                storageContainer.getLocomotiveRepository());
    }
}
