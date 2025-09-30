package com.github.alphameo.railways.repository.inmemory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.alphameo.railways.domain.Carriage;
import com.github.alphameo.railways.repository.Repository;

public class InMemoryCarriageRepository implements Repository<Carriage, Long> {

    private final InMemoryStorage<Carriage, Long> storage = new InMemoryStorage<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final HashSet<String> uniqueNumbers = new HashSet<>();

    @Override
    public Carriage add(Carriage carriage) throws IllegalArgumentException {
        if (carriage == null) {
            throw new IllegalArgumentException("carriage is null");
        }
        validate(carriage);
        var number = carriage.getNumber();
        if (uniqueNumbers.contains(number)) {
            throw new IllegalArgumentException("carriage number is not unique");
        }
        if (carriage.getId() == null) {
            long id = idGenerator.incrementAndGet();
            carriage.setId(id);
        }
        var id = carriage.getId();
        uniqueNumbers.add(number);
        storage.add(id, carriage);
        return carriage;
    }

    @Override
    public Optional<Carriage> getById(Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Carriage> getAll() {
        return storage.getAll();
    }

    @Override
    public boolean update(Carriage carriage) throws IllegalArgumentException {
        if (carriage == null) {
            throw new IllegalArgumentException("Invalid carriage: object is null");
        }
        validate(carriage);
        var oldNumber = storage.getById(carriage.getId()).get().getNumber();
        if (oldNumber != carriage.getNumber()) {
            uniqueNumbers.remove(oldNumber);
            uniqueNumbers.add(carriage.getNumber());
        }

        return storage.update(carriage.getId(), carriage);
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

    private void validate(Carriage carriage) throws IllegalArgumentException {
        if (carriage.getNumber() == null) {
            throw new IllegalArgumentException("Invalid carriage: number is null");
        }
        if (carriage.getCapacity() < 0) {
            throw new IllegalArgumentException("Invalid carriage: capacity < 0");
        }
    }
}
