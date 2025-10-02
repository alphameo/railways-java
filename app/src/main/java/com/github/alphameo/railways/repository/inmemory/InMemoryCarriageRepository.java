package com.github.alphameo.railways.repository.inmemory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.Carriage;
import com.github.alphameo.railways.repository.CarriageRepository;

public class InMemoryCarriageRepository implements CarriageRepository {

    private final InMemoryStorage<Carriage, Long> storage = new InMemoryStorage<>();
    private final HashSet<String> uniqueNumbers = new HashSet<>();
    private Long idGenerator = 0L;

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
            long id = ++idGenerator;
            carriage.setId(id);
        }
        var id = carriage.getId();
        uniqueNumbers.add(number);
        storage.add(id, carriage);
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
