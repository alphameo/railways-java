package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.exceptions.InMemoryException;

public class InMemoryCarriageRepository implements CarriageRepository {

    private final InMemoryStorage<Carriage, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;
    private final HashMap<String, Long> uniqueNumbers = new HashMap<>();

    @Override
    public Carriage create(Carriage carriage) throws InMemoryException {
        validate(carriage);
        var number = carriage.getNumber();
        if (uniqueNumbers.containsKey(number)) {
            throw new InMemoryException("carriage number is not unique");
        }
        if (carriage.getId() == null) {
            long id = ++idGenerator;
            carriage.setId(id);
        }
        var id = carriage.getId();
        uniqueNumbers.put(number, id);
        storage.create(id, carriage);
        return carriage;
    }

    @Override
    public Optional<Carriage> findById(Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Carriage> findAll() {
        return storage.findAll();
    }

    @Override
    public boolean update(Carriage carriage) throws InMemoryException {
        validate(carriage);
        var number = carriage.getNumber();
        var oldNumber = storage.getById(carriage.getId()).get().getNumber();
        if (oldNumber != number) {
            if (uniqueNumbers.containsKey(number)) {
                throw new InMemoryException("carriage number is not unique");
            } else {
                uniqueNumbers.remove(oldNumber);
            }
            uniqueNumbers.put(carriage.getNumber(), carriage.getId());
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

    @Override
    public Optional<Carriage> findByNumber(String number) {
        var id = uniqueNumbers.get(number);
        var carriage = storage.getById(id);
        if (carriage == null) {
            return Optional.empty();
        }
        return carriage;
    }

    private void validate(Carriage carriage) throws InMemoryException {
        if (carriage == null) {
            throw new IllegalArgumentException("carriage is null");
        }
        if (carriage.getId() == null) {
            throw new InMemoryException("Invalid carriage: id is null");
        }
        if (carriage.getNumber() == null) {
            throw new InMemoryException("Invalid carriage: number is null");
        }
        if (carriage.getCapacity() < 0) {
            throw new InMemoryException("Invalid carriage: capacity < 0");
        }
    }
}
