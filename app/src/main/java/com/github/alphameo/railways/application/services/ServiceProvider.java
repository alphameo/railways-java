package com.github.alphameo.railways.application.services;

import com.github.alphameo.railways.infrastructure.Storage;

import lombok.Getter;

public class ServiceProvider {

    @Getter
    private CarriageService carriageService;
    @Getter
    private LineService lineService;
    @Getter
    private LocomotiveService locomotiveService;
    @Getter
    private StationService stationService;
    @Getter
    private TrainService trainService;
    @Getter
    private TrainCompositionService trainCompositionService;

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
