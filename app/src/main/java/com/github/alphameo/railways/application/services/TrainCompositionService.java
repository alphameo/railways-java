package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.repositories.TrainCompositionCarriageRepository;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.repositories.TrainRepository;

public class TrainCompositionService {

    private TrainRepository trainRepository;
    private TrainCompositionRepository trainCompositionRepository;
    private TrainCompositionCarriageRepository trainCompositionCarriageRepository;
    private CarriageRepository carriageRepository;
    private LocomotiveRepository locomotiveRepository;

    public void assignTrain(final Long trainId, final Long trainCompositionId) {

    }

    public void disassignTrain(final Long trainId) {

    }

    public TrainComposition assembleTrainComposition(final List<Long> carriageIds, final Long locomotiveId) {

    }

    public TrainComposition disassembleTrainComposition(final Long trainCompositionId) {

    }
}
