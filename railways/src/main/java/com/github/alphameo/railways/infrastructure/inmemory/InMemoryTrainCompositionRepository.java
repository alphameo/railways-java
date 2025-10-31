package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;

import lombok.NonNull;

public class InMemoryTrainCompositionRepository implements TrainCompositionRepository {

    private final Map<Id, TrainComposition> storage;
    private final Map<Id, Locomotive> locoStorage;
    private final Map<Id, Carriage> carrStorage;

    public InMemoryTrainCompositionRepository(
            @NonNull final Map<Id, TrainComposition> storage,
            @NonNull final Map<Id, Locomotive> locomotiveStorage,
            @NonNull final Map<Id, Carriage> carriageSorage) {
        this.storage = storage;
        this.locoStorage = locomotiveStorage;
        this.carrStorage = carriageSorage;
    }

    @Override
    public void create(@NonNull final TrainComposition trainComposition) {
        final var id = trainComposition.getId();
        if (storage.containsKey(id)) {
            throw new InMemoryEntityAlreadyExistsException("TrainComposition", id);
        }

        storage.put(id, trainComposition);
    }

    @Override
    public Optional<TrainComposition> findById(final Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TrainComposition> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final TrainComposition trainComposition) {
        final var id = trainComposition.getId();
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(trainComposition.getClass().toString(), id);
        }

        storage.put(id, trainComposition);
    }

    @Override
    public void deleteById(@NonNull final Id id) {
        storage.remove(id);
    }

    public void validate(final TrainComposition trainComposition) {
        final var locoId = trainComposition.getLocomotiveId();
        if (locoStorage.get(locoId) == null) {
            throw new InMemoryEntityNotExistsException("Locomotive", locoId);
        }

        final var carrIds = trainComposition.getCarriageIds();
        for (final Id carrId : carrIds) {
            if (carrStorage.get(carrId) == null) {
                throw new InMemoryEntityNotExistsException("Carriage", carrId);
            }
        }
    }
}
