package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.TrainCompositionCarriage;
import com.github.alphameo.railways.domain.entities.TrainCompositionCarriage.TrainCompositionCarriageId;
import com.github.alphameo.railways.domain.repositories.TrainCompositionCarriageRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryConstraintException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;

public class InMemoryTrainCompositionCarriageRepository
        implements TrainCompositionCarriageRepository {

    private final InMemoryStorage<TrainCompositionCarriage, TrainCompositionCarriageId> storage = new InMemoryStorage<>();

    @Override
    public TrainCompositionCarriage create(final TrainCompositionCarriage trainCompositionCarriage) {
        validate(trainCompositionCarriage);

        return storage.create(trainCompositionCarriage.getId(), trainCompositionCarriage);
    }

    @Override
    public Optional<TrainCompositionCarriage> findById(final TrainCompositionCarriageId id) {
        validateId(id);

        return storage.getById(id);
    }

    @Override
    public List<TrainCompositionCarriage> findAll() {
        return storage.findAll();
    }

    @Override
    public TrainCompositionCarriage update(final TrainCompositionCarriage trainCompositionCarriage) {
        validate(trainCompositionCarriage);

        return storage.update(trainCompositionCarriage.getId(), trainCompositionCarriage);
    }

    @Override
    public void deleteById(final TrainCompositionCarriageId id) {
        validateId(id);

        storage.deleteById(id);
    }

    private void validateId(TrainCompositionCarriageId id) {
        if (id == null) {
            throw new InMemoryNotNullConstraintException("TrainCompositionCarriageId");
        }
        if (id.getTrainCompositionId() == null) {
            throw new InMemoryNotNullConstraintException("TrainCompositionCarriageId.trainCompositionId");
        }
        if (id.getCarriageId() == null) {
            throw new InMemoryNotNullConstraintException("TrainCompositionCarriageId.carriageId");
        }
    }

    public void validate(final TrainCompositionCarriage trainCompositionCarriage) {
        if (trainCompositionCarriage == null) {
            throw new IllegalArgumentException("TrainComposition cannot be null");
        }
        validateId(trainCompositionCarriage.getId());
        if (trainCompositionCarriage.getPosition() == null) {
            throw new InMemoryConstraintException("Invalid trainCompositionCarriage: position is null");
        }
        if (trainCompositionCarriage.getPosition() < 0) {
            throw new InMemoryConstraintException("Invalid trainCompositionCarriage: position < 0");
        }
    }
}
