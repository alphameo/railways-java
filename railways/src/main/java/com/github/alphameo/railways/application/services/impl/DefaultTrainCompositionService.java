package com.github.alphameo.railways.application.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.application.mapper.TrainCompositionMapper;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DefaultTrainCompositionService implements TrainCompositionService {

    private TrainCompositionRepository trainCompositionRepo;
    private CarriageRepository carriageRepo;
    private LocomotiveRepository locomotiveRepo;

    @Override
    public void assembleTrainComposition(@NonNull final TrainCompositionDto trainComposition) {
        final var locomotiveId = trainComposition.locomotiveId();
        final var carriageIds = trainComposition.carriageIds();

        final Optional<Locomotive> loc = locomotiveRepo.findById(locomotiveId);
        if (loc.isEmpty()) {
            throw new EntityNotFoundException("Locomotive", locomotiveId);
        }
        for (final var id : carriageIds) {
            final Optional<Carriage> car = carriageRepo.findById(id);
            if (car.isEmpty()) {
                throw new EntityNotFoundException("Carriage", id);
            }
        }
        try {
            final var trainComp = new TrainComposition(locomotiveId, carriageIds);
            trainCompositionRepo.create(trainComp);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public TrainCompositionDto findById(@NonNull final Id id) {
        final Optional<TrainComposition> out;
        try {
            out = trainCompositionRepo.findById(id);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("TrainComposition", id.toString());
        }

        return TrainCompositionMapper.toDto(out.get());
    }

    @Override
    public List<TrainCompositionDto> listAll() {
        try {
            return TrainCompositionMapper.toDtoList(trainCompositionRepo.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void disassembleTrainComposition(@NonNull final Id id) {
        try {
            trainCompositionRepo.deleteById(id);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
}
