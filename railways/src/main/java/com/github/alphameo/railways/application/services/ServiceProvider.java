package com.github.alphameo.railways.application.services;

public interface ServiceProvider {

    CarriageService getCarriageService();

    LineService getLineService();

    LocomotiveService getLocomotiveService();

    StationService getStationService();

    TrainService getTrainService();

    TrainCompositionService getTrainCompositionService();
}
