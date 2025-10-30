package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.TrainCompositionDto;

public interface TrainCompositionService {

    void assembleTrainComposition(TrainCompositionDto trainComposition);

    TrainCompositionDto findById(Long id);

    List<TrainCompositionDto> listAll();

    void disassembleTrainComposition(Long id);
}
