package com.github.alphameo.railways.repository.inmemory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.alphameo.railways.domain.Locomotive;
import com.github.alphameo.railways.repository.LocomotiveRepository;

public class InMemoryLocomotiveRepository implements LocomotiveRepository {

    private final InMemoryStorage<Locomotive, Long> storage = new InMemoryStorage<Locomotive, Long>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final HashSet<String> uniqueNumbers = new HashSet<>();

    @Override
    public Locomotive add(Locomotive locomotive) throws IllegalArgumentException {
        if (locomotive == null) {
            throw new IllegalArgumentException("Invalid locomotive: object is null");
        }
        validate(locomotive);
        var number = locomotive.getNumber();
        if (uniqueNumbers.contains(number)) {
            throw new IllegalArgumentException("Invalid locomotive: number is not unique");
        }
        if (locomotive.getId() == null) {
            long id = idGenerator.incrementAndGet();
            locomotive.setId(id);
        }

        storage.add(locomotive.getId(), locomotive);
        uniqueNumbers.add(number);
        return locomotive;
    }

    @Override
    public Optional<Locomotive> findById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Locomotive> findAll() {
        return storage.findAll();
    }

    @Override
    public boolean update(Locomotive locomotive) throws IllegalArgumentException {
        if (locomotive == null) {
            throw new IllegalArgumentException("Invalid locomotive: object is null");
        }
        validate(locomotive);
        var oldNumber = storage.getById(locomotive.getId()).get().getNumber();
        if (oldNumber != locomotive.getNumber()) {
            uniqueNumbers.remove(oldNumber);
            uniqueNumbers.add(locomotive.getNumber());
        }

        return storage.update(locomotive.getId(), locomotive);
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

    private void validate(Locomotive locomotive) {
        if (locomotive.getNumber() == null) {
            throw new IllegalArgumentException("Invalid locomotive: number is null");
        }
        if (locomotive.getModel() == null) {
            throw new IllegalArgumentException("Invalid locomotive: model is null");
        }
    }
}
