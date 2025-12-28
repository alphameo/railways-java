package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryUniqueCounstraintException;

import lombok.NonNull;

public class InMemoryTrainRepository implements TrainRepository {

    private final Map<Id, Train> storage;
    private final HashMap<MachineNumber, Id> uniqueNumberIds = new HashMap<>();
    private final Map<Id, TrainComposition> trainCompoStorage;

    public InMemoryTrainRepository(
            @NonNull final Map<Id, Train> storage,
            @NonNull final Map<Id, TrainComposition> trainCompositionStorage) {
        this.storage = storage;
        this.trainCompoStorage = trainCompositionStorage;
    }

    @Override
    public void create(@NonNull final Train train) {
        validate(train);
        final var number = train.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryUniqueCounstraintException("Train.number");
        }

        var id = train.getId();
        if (storage.containsKey(id)) {
            throw new InMemoryEntityAlreadyExistsException("Train", id);
        }

        uniqueNumberIds.put(train.getNumber(), id);
        storage.put(id, train);
    }

    @Override
    public Optional<Train> findById(@NonNull final Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Train> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Train train) {
        validate(train);

        final var id = train.getId();
        if (id == null) {
            throw new InMemoryException("id cannot be null");
        }
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(train.getClass().toString(), id);
        }
        final var number = train.getNumber();
        final var oldNumber = storage.get(id).getNumber();
        if (oldNumber != number) {
            if (uniqueNumberIds.containsKey(number)) {
                throw new InMemoryUniqueCounstraintException("Train.number");
            }
            uniqueNumberIds.remove(oldNumber);
            uniqueNumberIds.put(number, id);
        }

        storage.put(id, train);
    }

    @Override
    public void deleteById(@NonNull final Id id) {
        final var delCandidate = storage.remove(id);
        if (delCandidate != null) {
            uniqueNumberIds.remove(delCandidate.getNumber());
        }
    }

    @Override
    public Optional<Train> findByNumber(final MachineNumber number) {
        final var id = uniqueNumberIds.get(number);
        final var train = storage.get(id);
        return Optional.ofNullable(train);
    }

    @Override
    public List<com.github.alphameo.railways.domain.valueobjects.ScheduleEntry> getScheduleForTrain(Id trainId) {
        Train train = storage.get(trainId);
        return train != null ? train.getSchedule() : new ArrayList<>();
    }

    private void validate(final Train train) {
        final var trainCompoId = train.getTrainCompositionId();
        if (trainCompoStorage.get(trainCompoId) == null) {
            throw new EntityNotFoundException("TrainComposition", trainCompoId);
        }
    }
}
