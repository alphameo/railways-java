package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryException;

import lombok.NonNull;

public class InMemoryTrainCompositionRepository implements TrainCompositionRepository {

    private final Map<Long, TrainComposition> storage;
    private final Map<Long, Locomotive> locoStorage;
    private final Map<Long, Carriage> carrStorage;
    private Long idGenerator = 0L;

    public InMemoryTrainCompositionRepository(
            @NonNull final Map<Long, TrainComposition> storage,
            @NonNull final Map<Long, Locomotive> locomotiveStorage,
            @NonNull final Map<Long, Carriage> carriageSorage) {
        this.storage = storage;
        this.locoStorage = locomotiveStorage;
        this.carrStorage = carriageSorage;
    }

    @Override
    public void create(@NonNull final TrainComposition trainComposition) {
        Long id = trainComposition.getId();
        if (trainComposition.getId() == null) {
            id = ++idGenerator;
        } else {
            if (storage.containsKey(id)) {
                throw new InMemoryEntityAlreadyExistsException("TrainComposition", id);
            }
        }

        final var newTrainComposition = createTrainComposition(id, trainComposition);
        storage.put(id, newTrainComposition);
    }

    @Override
    public Optional<TrainComposition> findById(final Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TrainComposition> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final TrainComposition trainComposition) {
        final var id = trainComposition.getId();
        if (id == null) {
            throw new InMemoryException("id cannot be null");
        }
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(trainComposition.getClass().toString(), id);
        }

        final var newTC = createTrainComposition(id, trainComposition);
        storage.put(id, newTC);
    }

    @Override
    public void deleteById(final Long id) {
        storage.remove(id);
    }

    public void validate(final TrainComposition trainComposition) {
        final var locoId = trainComposition.getLocomotiveId();
        if (locoStorage.get(locoId) == null) {
            throw new InMemoryEntityNotExistsException("Locomotive", locoId);
        }

        final var carrIds = trainComposition.getCarriageIds();
        for (final Long carrId : carrIds) {
            if (carrStorage.get(carrId) == null) {
                throw new InMemoryEntityNotExistsException("Carriage", carrId);
            }
        }
    }

    private static TrainComposition createTrainComposition(final long id, final TrainComposition t) {
        return new TrainComposition(
                id,
                t.getLocomotiveId(),
                t.getCarriageIds());
    }
}
