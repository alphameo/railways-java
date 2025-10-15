package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;

import lombok.NonNull;

public class InMemoryTrainCompositionRepository implements TrainCompositionRepository {

    private final Map<Long, TrainComposition> storage = new HashMap<>();
    private Long idGenerator = 0L;

    @Override
    public void create(@NonNull final TrainComposition trainComposition) {
        validate(trainComposition);

        Long id = trainComposition.getId();
        if (trainComposition.getId() == null) {
            id = ++idGenerator;
        } else {
            if (storage.containsKey(id)) {
                throw new InMemoryEntityAlreadyExistsException("Station", id);
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
    public void update(final TrainComposition trainComposition) {
        validate(trainComposition);

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

    private static void validate(final TrainComposition trainComposition) {
        if (trainComposition == null) {
            throw new IllegalArgumentException("TrainComposition cannot be null");
        }
        if (trainComposition.getLocomotiveId() == null) {
            throw new InMemoryNotNullConstraintException("TrainComposition.locomotiveId");
        }
        if (trainComposition.getCarriageIds() == null) {
            throw new InMemoryNotNullConstraintException("TrainComposition.carriageIds");
        }
    }

    private static TrainComposition createTrainComposition(final long id, final TrainComposition t) {
        return new TrainComposition(
                id,
                t.getLocomotiveId(),
                t.getCarriageIds());
    }
}
