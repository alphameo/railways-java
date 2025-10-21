package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.entities.TrainComposition;

import lombok.Getter;

public class InMemoryStorage {

    private static InMemoryStorage INSTANCE;

    @Getter
    private InMemoryCarriageRepository carrRepo;
    @Getter
    private InMemoryLineRepository lineRepo;
    @Getter
    private InMemoryLocomotiveRepository locoRepo;
    @Getter
    private InMemoryStationRepository stationRepo;
    @Getter
    private InMemoryTrainRepository trainRepo;
    @Getter
    private InMemoryTrainCompositionRepository trainCompoRepo;

    private InMemoryStorage() {
        final var carrStorage = new HashMap<Long, Carriage>();
        final var lineStorage = new HashMap<Long, Line>();
        final var locoStorage = new HashMap<Long, Locomotive>();
        final var stationStorage = new HashMap<Long, Station>();
        final var trainStorage = new HashMap<Long, Train>();
        final var trainCompoStorage = new HashMap<Long, TrainComposition>();
        this.carrRepo = new InMemoryCarriageRepository(carrStorage);
        this.lineRepo = new InMemoryLineRepository(lineStorage, stationStorage);
        this.locoRepo = new InMemoryLocomotiveRepository(locoStorage);
        this.stationRepo = new InMemoryStationRepository(stationStorage);
        this.trainRepo = new InMemoryTrainRepository(trainStorage, trainCompoStorage);
    }

    public static InMemoryStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryStorage();
        }

        return INSTANCE;
    }
}
