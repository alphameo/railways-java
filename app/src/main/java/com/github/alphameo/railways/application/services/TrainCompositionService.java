package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.NonNull;

public class TrainCompositionService {

    private TrainCompositionRepository trainCompositionRepo;
    private CarriageRepository carriageRepo;
    private LocomotiveRepository locomotiveRepo;

    public void assembleTrainComposition(Long locomotiveId, List<Long> carriageIds) {
        final Optional<Locomotive> loc = locomotiveRepo.findById(locomotiveId);
        if (loc.isEmpty()) {
            throw new EntityNotFoundException("Locomotive", locomotiveId);
        }
        for (long id : carriageIds) {
            final Optional<Carriage> car = carriageRepo.findById(id);
            if (car.isEmpty()) {
                throw new EntityNotFoundException("Carriage", id);
            }
        }
        try {
            final var trainComp = new TrainComposition(null, locomotiveId, carriageIds);
            trainCompositionRepo.create(trainComp);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public TrainComposition findById(@NonNull final Long id) {
        final Optional<TrainComposition> out;
        try {
            out = trainCompositionRepo.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Station", id.toString());
        }

        return out.get();
    }

    public List<TrainComposition> listAll() {
        try {
            return trainCompositionRepo.findAll();
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void disassembleTrainComposition(final Long id) {
        try {
            trainCompositionRepo.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
