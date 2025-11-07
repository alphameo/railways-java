package com.github.alphameo.railways.application.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.application.mapper.ScheduleEntryMapper;
import com.github.alphameo.railways.application.mapper.TrainMapper;
import com.github.alphameo.railways.application.services.TrainService;
import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.domain.valueobjects.ScheduleEntry;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DefaultTrainService implements TrainService {

    private final TrainRepository trainRepo;
    private final TrainCompositionRepository trainCompositionRepo;

    @Override
    public void registerTrain(@NonNull final TrainDto train) {
        final var trainEntity = TrainMapper.toEntity(train);
        final var trainCompoId = trainEntity.getTrainCompositionId();
        final var trainCompo = trainCompositionRepo.findById(trainCompoId);
        if (trainCompo.isEmpty()) {
            throw new EntityNotFoundException("TrainComposition", trainCompoId);
        }
        try {
            final var number = new MachineNumber(train.number());
            final var schedule = trainEntity.getSchedule();
            final List<ScheduleEntry> valSchedule = new ArrayList<>();
            for (final var scheduleEntry : schedule) {
                final var valScheduleEntry = new ScheduleEntry(scheduleEntry.getStationId(),
                        scheduleEntry.getArrivalTime(),
                        scheduleEntry.getDepartureTime());
                valSchedule.add(valScheduleEntry);
            }
            final var valTrain = new Train(number, trainCompoId);
            trainRepo.create(valTrain);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public TrainDto findTrainById(@NonNull final String id) {
        final Optional<Train> out;
        try {
            final var valId = Id.fromString(id);
            out = trainRepo.findById(valId);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Train", id.toString());
        }

        return TrainMapper.toDto(out.get());
    }

    @Override
    public TrainDto findTrainByNumber(@NonNull final String number) {
        final Optional<Train> out;
        try {
            final var valNumber = new MachineNumber(number);
            out = trainRepo.findByNumber(valNumber);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException(String.format("Train with number=%s not exists", number));
        }

        return TrainMapper.toDto(out.get());
    }

    @Override
    public List<TrainDto> listAll() {
        try {
            return TrainMapper.toDtoList(trainRepo.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unregisterTrain(@NonNull final String id) {
        try {
            final var valId = Id.fromString(id);
            trainRepo.deleteById(valId);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void insertScheduleEntry(
            @NonNull final String trainId,
            @NonNull final ScheduleEntryDto scheduleEntry,
            final int orderIndex) {
        final var valId = Id.fromString(trainId);
        final var trainOpt = trainRepo.findById(valId);
        if (trainOpt.isEmpty()) {
            throw new EntityNotFoundException("Train", trainId);
        }
        try {
            final var valScheduleEntry = ScheduleEntryMapper.toValueObject(scheduleEntry);
            final var train = trainOpt.get();
            train.insertScheduleEntry(valScheduleEntry, orderIndex);
            trainRepo.update(train);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeScheduleEntry(@NonNull final String trainId, final int orderIndex) {
        final var valId = Id.fromString(trainId);
        final var trainOpt = trainRepo.findById(valId);
        if (trainOpt.isEmpty()) {
            throw new EntityNotFoundException("Train", trainId);
        }
        try {
            final var train = trainOpt.get();
            train.removeScheduleEntry(orderIndex);
            trainRepo.update(train);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSchedule(@NonNull final String trainId, @NonNull final List<ScheduleEntryDto> schedule) {
        final var valId = Id.fromString(trainId);
        final var trainOpt = trainRepo.findById(valId);
        if (trainOpt.isEmpty()) {
            throw new EntityNotFoundException("Train", trainId);
        }
        try {
            final var valSchedule = ScheduleEntryMapper.toValueObjectList(schedule);
            final var train = trainOpt.get();
            train.updateSchedule(valSchedule);
            trainRepo.update(train);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
}
