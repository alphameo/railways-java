package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;

public class InMemoryTrainCompositionRepository implements TrainCompositionRepository {

    private final InMemoryStorage<TrainComposition, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public TrainComposition add(TrainComposition trainComposition) throws IllegalArgumentException {
        if (trainComposition == null) {
            throw new IllegalArgumentException("Invalid trainComposition: object is null");
        }
        validate(trainComposition);
        if (trainComposition.getId() == null) {
            long id = ++idGenerator;
            trainComposition.setId(id);
        }

        storage.add(trainComposition.getId(), trainComposition);
        return trainComposition;
    }

    @Override
    public Optional<TrainComposition> findById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<TrainComposition> findAll() {
        return storage.findAll();
    }

    @Override
    public boolean update(TrainComposition trainComposition) throws IllegalArgumentException {
        if (trainComposition == null) {
            throw new IllegalArgumentException("Invalid trainComposition: object is null");
        }
        validate(trainComposition);

        return storage.update(trainComposition.getId(), trainComposition);
    }

    @Override
    public boolean deleteById(Long id) throws IllegalArgumentException {
        return storage.deleteById(id) != null;
    }

    private void validate(TrainComposition trainComposition) throws IllegalArgumentException {
        if (trainComposition.getTrain_id() == null) {
            throw new IllegalArgumentException("invalid trainComposition: trainId is null");
        }
    }
}
