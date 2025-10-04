package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryTrainRepository implements TrainRepository {

    private final InMemoryStorage<Train, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;
    private final HashMap<String, Long> uniqueNumberIds = new HashMap<>();

    @Override
    public Train create(Train train) {
        validate(train);
        final var number = train.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryException("Train.number is not unique");
        }

        if (train.getId() == null) {
            final long id = ++idGenerator;
            train.setId(id);
        }
        final var id = train.getId();
        uniqueNumberIds.put(number, id);
        return storage.create(id, train);
    }

    @Override
    public Optional<Train> findById(Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Train> findAll() {
        return storage.findAll();
    }

    @Override
    public Train update(Train train) {
        validate(train);

        final var number = train.getNumber();
        final var oldNumber = storage.getById(train.getId()).get().getNumber();
        if (oldNumber != number) {
            if (uniqueNumberIds.containsKey(number)) {
                throw new InMemoryException("Train.number is not unique");
            }
            uniqueNumberIds.remove(oldNumber);
            uniqueNumberIds.put(number, train.getId());
        }

        return storage.update(train.getId(), train);
    }

    @Override
    public void deleteById(Long id) {
        final var delCandidate = storage.getById(id);
        storage.deleteById(id);
        final var number = delCandidate.get().getNumber();
        uniqueNumberIds.remove(number);
    }

    public Optional<Train> findByNumber(final String number) {
        final var id = uniqueNumberIds.get(number);
        final var train = storage.getById(id);
        if (train == null) {
            return Optional.empty();
        }
        return train;
    }

    private void validate(Train train) {
        if (train == null) {
            throw new InMemoryException("Train cannot be null");
        }
        if (train.getId() == null) {
            throw new InMemoryException("Train.id cannot be null");
        }
        if (train.getNumber() == null) {
            throw new InMemoryException("Train.number cannot be null");
        }
    }
}
