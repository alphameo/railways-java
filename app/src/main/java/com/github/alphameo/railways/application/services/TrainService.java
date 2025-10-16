package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.domain.valueobjects.ScheduleEntry;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.NonNull;

public class TrainService {

    private TrainRepository trainRepo;
    private TrainCompositionRepository trainCompositionRepo;

    public void register(final MachineNumber number, final Long trainCompositionId,
            final List<ScheduleEntry> schedule) {
        final var trainCompo = trainCompositionRepo.findById(trainCompositionId);
        if (trainCompo.isEmpty()) {
            throw new EntityNotFoundException("TrainComposition", trainCompositionId);
        }
        try {
            final var train = new Train(null, number, trainCompositionId, schedule);
            trainRepo.create(train);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Train findById(@NonNull Long id) {
        final Optional<Train> out;
        try {
            out = trainRepo.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Train", id.toString());
        }

        return out.get();
    }

    public List<Train> listAll() {
        try {
            return trainRepo.findAll();
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void unregister(Long id) {
        try {
            trainRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
