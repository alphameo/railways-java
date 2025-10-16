package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryUniqueCounstraintException;

import lombok.NonNull;

public class InMemoryTrainRepository implements TrainRepository {

    private final Map<Long, Train> storage = new HashMap<>();
    private Long idGenerator = 0L;
    private final HashMap<MachineNumber, Long> uniqueNumberIds = new HashMap<>();

    @Override
    public void create(@NonNull Train train) {
        final var number = train.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryUniqueCounstraintException("Train.number");
        }

        Long id = train.getId();
        if (train.getId() == null) {
            id = ++idGenerator;
        } else {
            if (storage.containsKey(id)) {
                throw new InMemoryEntityAlreadyExistsException("Carriage", id);
            }
        }

        final var newTrain = createTrain(id, train);
        uniqueNumberIds.put(newTrain.getNumber(), id);
        storage.put(id, newTrain);
    }

    @Override
    public Optional<Train> findById(@NonNull Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Train> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull Train train) {
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

        final var newTrain = createTrain(id, train);
        storage.put(id, newTrain);
    }

    @Override
    public void deleteById(Long id) {
        final var delCandidate = storage.remove(id);
        if (delCandidate != null) {
            uniqueNumberIds.remove(delCandidate.getNumber());
        }
    }

    public Optional<Train> findByNumber(final MachineNumber number) {
        final var id = uniqueNumberIds.get(number);
        final var train = storage.get(id);
        return Optional.ofNullable(train);
    }

    private static Train createTrain(final long id, final Train t) {
        return new Train(
                id,
                t.getNumber(),
                t.getTrainCompositionId(),
                t.getSchedule());
    }
}
