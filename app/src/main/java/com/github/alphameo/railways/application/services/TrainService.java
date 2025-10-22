package com.github.alphameo.railways.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.TrainDto;
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

    public void register(@NonNull final TrainDto train) {
        final var trainCompoId = train.trainCompositionId();
        final var trainCompo = trainCompositionRepo.findById(trainCompoId);
        if (trainCompo.isEmpty()) {
            throw new EntityNotFoundException("TrainComposition", trainCompoId);
        }
        try {
            final var number = new MachineNumber(train.number());
            final var schedule = train.schedule();
            final List<ScheduleEntry> valSchedule = new ArrayList<>();
            for (var scheduleEntry : schedule) {
                final var valScheduleEntry = new ScheduleEntry(scheduleEntry.stationId(), scheduleEntry.arrivalTime(), scheduleEntry.departureTime());
                valSchedule.add(valScheduleEntry);
            }
            final var valTrain = new Train(null, number, trainCompoId, valSchedule);
            trainRepo.create(valTrain);
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
