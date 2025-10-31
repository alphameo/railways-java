package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.domain.valueobjects.Id;

public interface TrainCompositionService {

    void assembleTrainComposition(TrainCompositionDto trainComposition);

    TrainCompositionDto findById(Id id);

    List<TrainCompositionDto> listAll();

    void disassembleTrainComposition(Id id);
}
