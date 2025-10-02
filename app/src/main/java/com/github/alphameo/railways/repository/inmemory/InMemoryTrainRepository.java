package com.github.alphameo.railways.repository.inmemory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.alphameo.railways.domain.Train;
import com.github.alphameo.railways.repository.TrainRepository;

public class InMemoryTrainRepository implements TrainRepository {

    private final InMemoryStorage<Train, Long> storage = new InMemoryStorage<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final HashSet<String> uniqueNumbers = new HashSet<>();

    @Override
    public Train add(Train train) throws IllegalArgumentException {
        if (train == null) {
            throw new IllegalArgumentException("Invalid train: object is null");
        }
        validate(train);
        var number = train.getNumber();
        if (uniqueNumbers.contains(number)) {
            throw new IllegalArgumentException("Invalid train: number is not unique");
        }
        if (train.getId() == null) {
            long id = idGenerator.incrementAndGet();
            train.setId(id);
        }

        storage.add(train.getId(), train);
        uniqueNumbers.add(number);
        return train;
    }

    @Override
    public Optional<Train> findById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Train> findAll() {
        return storage.findAll();
    }

    @Override
    public boolean update(Train train) throws IllegalArgumentException {
        if (train == null) {
            throw new IllegalArgumentException("Invalid train: object is null");
        }
        validate(train);
        var oldNumber = storage.getById(train.getId()).get().getNumber();
        if (oldNumber != train.getNumber()) {
            uniqueNumbers.remove(oldNumber);
            uniqueNumbers.add(train.getNumber());
        }

        return storage.update(train.getId(), train);
    }

    @Override
    public boolean deleteById(Long id) throws IllegalArgumentException {
        var deleted = storage.deleteById(id);
        if (deleted == null) {
            return false;
        }
        var number = deleted.getNumber();
        uniqueNumbers.remove(number);
        return true;
    }

    private void validate(Train train) {
        if (train.getNumber() == null) {
            throw new IllegalArgumentException("Invalid train: number is null");
        }
    }
}
