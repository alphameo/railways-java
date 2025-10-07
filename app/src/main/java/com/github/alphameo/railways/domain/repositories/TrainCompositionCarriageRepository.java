package com.github.alphameo.railways.domain.repositories;

import java.util.List;

import com.github.alphameo.railways.domain.entities.TrainCompositionCarriage;
import com.github.alphameo.railways.domain.entities.TrainCompositionCarriage.TrainCompositionCarriageId;

public interface TrainCompositionCarriageRepository
        extends Repository<TrainCompositionCarriage, TrainCompositionCarriageId> {

    List<Long> findTrainCompositionIdsByCarriageId(Long trainCompositionId);

    List<Long> findCarriageIdsByTrainCompositionId(Long carriageId);
}
