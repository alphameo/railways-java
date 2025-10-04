package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.TrainCompositionCarriage;
import com.github.alphameo.railways.domain.entities.TrainCompositionCarriage.TrainCompositionCarriageId;
import com.github.alphameo.railways.domain.repositories.TrainCompositionCarriageRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryTrainCompositionCarriageRepository
        implements TrainCompositionCarriageRepository {

    private final InMemoryStorage<TrainCompositionCarriage, TrainCompositionCarriageId> storage = new InMemoryStorage<>();

    @Override
    public TrainCompositionCarriage create(TrainCompositionCarriage trainCompositionCarriage) {
        validate(trainCompositionCarriage);

        return storage.create(trainCompositionCarriage.getId(), trainCompositionCarriage);
    }

    @Override
    public Optional<TrainCompositionCarriage> findById(TrainCompositionCarriageId id) {
        if (id == null) {
            throw new InMemoryException("TrainCompositionCarriageId cannot be null");
        }
        if (id.getTrainCompositionId() == null) {
            throw new InMemoryException("TrainCompositionCarriageId.trainCompositionId cannot be null");
        }
        if (id.getCarriageId() == null) {
            throw new InMemoryException("TrainCompositionCarriageId.carriageId cannot be null");
        }

        return storage.getById(id);
    }

    @Override
    public List<TrainCompositionCarriage> findAll() {
        return storage.findAll();
    }

    @Override
    public TrainCompositionCarriage update(TrainCompositionCarriage trainCompositionCarriage) {
        validate(trainCompositionCarriage);

        return storage.update(trainCompositionCarriage.getId(), trainCompositionCarriage);
    }

    @Override
    public void deleteById(TrainCompositionCarriageId id) {
        if (id == null) {
            throw new InMemoryException("TrainCompositionCarriageId cannot be null");
        }
        if (id.getTrainCompositionId() == null) {
            throw new InMemoryException("TrainCompositionCarriageId.trainCompositionId cannot be null");
        }
        if (id.getCarriageId() == null) {
            throw new InMemoryException("TrainCompositionCarriageId.carriageId cannot be null");
        }

        storage.deleteById(id);
    }

    public void validate(TrainCompositionCarriage trainCompositionCarriage) {
        if (trainCompositionCarriage == null) {
            throw new InMemoryException("Invalid trainComposition: object is null");
        }
        if (trainCompositionCarriage.getTrainCompositionId() == null) {
            throw new InMemoryException("Invalid trainCompositionCarriage: trainCompositionId is null");
        }
        if (trainCompositionCarriage.getCarriageId() == null) {
            throw new InMemoryException("Invalid trainCompositionCarriage: carriageId is null");
        }
        if (trainCompositionCarriage.getPosition() == null) {
            throw new InMemoryException("Invalid trainCompositionCarriage: position is null");
        }
        if (trainCompositionCarriage.getPosition() < 0) {
            throw new InMemoryException("Invalid trainCompositionCarriage: position < 0");
        }
    }
}
