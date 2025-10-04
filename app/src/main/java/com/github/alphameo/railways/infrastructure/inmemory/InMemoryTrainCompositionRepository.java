package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryTrainCompositionRepository implements TrainCompositionRepository {

    private final InMemoryStorage<TrainComposition, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public TrainComposition create(TrainComposition trainComposition) {
        validate(trainComposition);
        if (trainComposition.getId() == null) {
            long id = ++idGenerator;
            trainComposition.setId(id);
        }

        storage.create(trainComposition.getId(), trainComposition);
        return trainComposition;
    }

    @Override
    public Optional<TrainComposition> findById(Long id) {
        return storage.getById(id);
    }

    @Override
    public List<TrainComposition> findAll() {
        return storage.findAll();
    }

    @Override
    public TrainComposition update(TrainComposition trainComposition) {
        validate(trainComposition);

        return storage.update(trainComposition.getId(), trainComposition);
    }

    @Override
    public void deleteById(Long id) {
        storage.deleteById(id);
    }

    private void validate(TrainComposition trainComposition) {
        if (trainComposition == null) {
            throw new InMemoryException("TrainComposition.object cannot be null");
        }
        if (trainComposition.getTrainId() == null) {
            throw new InMemoryException("TrainComposition.trainId cannot be null");
        }
    }
}
