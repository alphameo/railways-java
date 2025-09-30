package com.github.alphameo.railways.repository.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.TrainCompositionCarriage;
import com.github.alphameo.railways.domain.TrainCompositionCarriage.TrainCompositionCarriageId;
import com.github.alphameo.railways.repository.TrainCompositionCarriageRepository;

public class InMemoryTrainCompositionCarriageRepository
        implements TrainCompositionCarriageRepository {

    private final InMemoryStorage<TrainCompositionCarriage, TrainCompositionCarriageId> storage = new InMemoryStorage<>();

    @Override
    public TrainCompositionCarriage add(TrainCompositionCarriage trainCompositionCarriage)
            throws IllegalArgumentException {
        if (trainCompositionCarriage == null) {
            throw new IllegalArgumentException("Invalid trainComposition: object is null");
        }
        validate(trainCompositionCarriage);
        if (trainCompositionCarriage.getId() == null) {
            throw new IllegalArgumentException("Invalid trainComposition: id is null");
        }

        storage.add(trainCompositionCarriage.getId(), trainCompositionCarriage);
        return trainCompositionCarriage;
    }

    @Override
    public Optional<TrainCompositionCarriage> getById(TrainCompositionCarriageId id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<TrainCompositionCarriage> getAll() {
        return storage.getAll();
    }

    @Override
    public boolean update(TrainCompositionCarriage trainComposition) throws IllegalArgumentException {
        if (trainComposition == null) {
            throw new IllegalArgumentException("Invalid trainComposition: object is null");
        }
        validate(trainComposition);

        return storage.update(trainComposition.getId(), trainComposition);
    }

    @Override
    public boolean deleteById(TrainCompositionCarriageId id) throws IllegalArgumentException {
        return storage.deleteById(id) != null;
    }

    public void validate(TrainCompositionCarriage trainCompositionCarriage) {
        if (trainCompositionCarriage.getTrainCompositionId() == null) {
            throw new IllegalArgumentException("Invalid trainCompositionCarriage: trainCompositionId is null");
        }
        if (trainCompositionCarriage.getCarriageId() == null) {
            throw new IllegalArgumentException("Invalid trainCompositionCarriage: carriageId is null");
        }
        if (trainCompositionCarriage.getPosition() == null) {
            throw new IllegalArgumentException("Invalid trainCompositionCarriage: position is null");
        }
        if (trainCompositionCarriage.getPosition() < 0) {
            throw new IllegalArgumentException("Invalid trainCompositionCarriage: position < 0");
        }
    }
}
