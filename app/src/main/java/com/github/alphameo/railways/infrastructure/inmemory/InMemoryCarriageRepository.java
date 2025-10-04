package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryCarriageRepository implements CarriageRepository {

    private final InMemoryStorage<Carriage, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;
    private final HashMap<String, Long> uniqueNumberIds = new HashMap<>();

    @Override
    public Carriage create(final Carriage carriage) {
        validate(carriage);
        final var number = carriage.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryException("Carriage.number is not unique");
        }
        if (carriage.getId() == null) {
            final long id = ++idGenerator;
            carriage.setId(id);
        }
        final var id = carriage.getId();
        uniqueNumberIds.put(number, id);
        return storage.create(id, carriage);
    }

    @Override
    public Optional<Carriage> findById(final Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Carriage> findAll() {
        return storage.findAll();
    }

    @Override
    public Carriage update(final Carriage carriage) {
        validate(carriage);
        final var number = carriage.getNumber();
        final var oldNumber = storage.getById(carriage.getId()).get().getNumber();
        if (oldNumber != number) {
            if (uniqueNumberIds.containsKey(number)) {
                throw new InMemoryException("Carriage.number is not unique");
            } else {
                uniqueNumberIds.remove(oldNumber);
            }
            uniqueNumberIds.put(carriage.getNumber(), carriage.getId());
        }

        return storage.update(carriage.getId(), carriage);
    }

    @Override
    public void deleteById(final Long id) {
        final var delCandidate = storage.getById(id);
        storage.deleteById(id);
        final var number = delCandidate.get().getNumber();
        uniqueNumberIds.remove(number);
    }

    @Override
    public Optional<Carriage> findByNumber(final String number) {
        final var id = uniqueNumberIds.get(number);
        final var carriage = storage.getById(id);
        if (carriage == null) {
            return Optional.empty();
        }
        return carriage;
    }

    private void validate(final Carriage carriage) {
        if (carriage == null) {
            throw new InMemoryException("Carriage cannot be null");
        }
        if (carriage.getId() == null) {
            throw new InMemoryException("Carriage.id cannot null");
        }
        if (carriage.getNumber() == null) {
            throw new InMemoryException("Carriage.number cannot null");
        }
        if (carriage.getCapacity() < 0) {
            throw new InMemoryException("Carriage.capacity should be >= 0");
        }
    }
}
