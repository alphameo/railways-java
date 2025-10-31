package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.valueobjects.Id;
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
        final var carrStorage = new HashMap<Id, Carriage>();
        final var lineStorage = new HashMap<Id, Line>();
        final var locoStorage = new HashMap<Id, Locomotive>();
        final var stationStorage = new HashMap<Id, Station>();
        final var trainStorage = new HashMap<Id, Train>();
        final var trainCompoStorage = new HashMap<Id, TrainComposition>();
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
