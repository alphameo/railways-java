package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.infrastructure.Storage;

import lombok.Getter;

@Getter
public class InMemoryStorage implements Storage {

    private static InMemoryStorage INSTANCE;

    private final InMemoryCarriageRepository carriageRepository;
    private final InMemoryLineRepository lineRepository;
    private final InMemoryLocomotiveRepository locomotiveRepository;
    private final InMemoryStationRepository stationRepository;
    private final InMemoryTrainRepository trainRepository;
    private final InMemoryTrainCompositionRepository trainCompositionRepository;

    private InMemoryStorage() {
        final var carrStorage = new HashMap<Long, Carriage>();
        final var lineStorage = new HashMap<Long, Line>();
        final var locoStorage = new HashMap<Long, Locomotive>();
        final var stationStorage = new HashMap<Long, Station>();
        final var trainStorage = new HashMap<Long, Train>();
        final var trainCompoStorage = new HashMap<Long, TrainComposition>();
        this.carriageRepository = new InMemoryCarriageRepository(carrStorage);
        this.lineRepository = new InMemoryLineRepository(lineStorage, stationStorage);
        this.locomotiveRepository = new InMemoryLocomotiveRepository(locoStorage);
        this.stationRepository = new InMemoryStationRepository(stationStorage);
        this.trainRepository = new InMemoryTrainRepository(trainStorage, trainCompoStorage);
        this.trainCompositionRepository = new InMemoryTrainCompositionRepository(
                trainCompoStorage,
                locoStorage,
                carrStorage);
    }

    public static InMemoryStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryStorage();
        }

        return INSTANCE;
    }
}
