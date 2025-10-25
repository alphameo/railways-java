package com.github.alphameo.railways.infrastructure;

import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.repositories.TrainRepository;

public interface Storage {
    CarriageRepository getCarriageRepository();

    LineRepository getLineRepository();

    LocomotiveRepository getLocomotiveRepository();

    StationRepository getStationRepository();

    TrainRepository getTrainRepository();

    TrainCompositionRepository getTrainCompositionRepository();
}
