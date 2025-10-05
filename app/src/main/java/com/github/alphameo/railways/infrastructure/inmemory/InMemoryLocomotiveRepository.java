package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryConstraintException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryUniqueCounstraintException;

public class InMemoryLocomotiveRepository implements LocomotiveRepository {

    private final InMemoryStorage<Locomotive, Long> storage = new InMemoryStorage<Locomotive, Long>();
    private Long idGenerator = 0L;
    private final HashMap<String, Long> uniqueNumberIds = new HashMap<>();

    @Override
    public Locomotive create(final Locomotive locomotive) {
        validate(locomotive);
        final var number = locomotive.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryConstraintException("Locomotive.number is not unique");
        }

        if (locomotive.getId() == null) {
            final long id = ++idGenerator;
            locomotive.setId(id);
        }
        final var id = locomotive.getId();
        uniqueNumberIds.put(number, id);
        return storage.create(locomotive.getId(), locomotive);
    }

    @Override
    public Optional<Locomotive> findById(final Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Locomotive> findAll() {
        return storage.findAll();
    }

    @Override
    public Locomotive update(final Locomotive locomotive) {
        validate(locomotive);
        final var number = locomotive.getNumber();
        final var oldNumber = storage.getById(locomotive.getId()).get().getNumber();
        if (oldNumber != number) {
            if (uniqueNumberIds.containsKey(number)) {
                throw new InMemoryUniqueCounstraintException("Locomotive.number");
            }
            uniqueNumberIds.remove(oldNumber);
            uniqueNumberIds.put(number, locomotive.getId());
        }

        return storage.update(locomotive.getId(), locomotive);
    }

    @Override
    public void deleteById(final Long id) {
        final var delCandidate = storage.getById(id);
        storage.deleteById(id);
        final var number = delCandidate.get().getNumber();
        uniqueNumberIds.remove(number);
    }

    @Override
    public Optional<Locomotive> findByNumber(final String number) {
        final var id = uniqueNumberIds.get(number);
        final var locomotive = storage.getById(id);
        if (locomotive == null) {
            return Optional.empty();
        }
        return locomotive;
    }

    private void validate(final Locomotive locomotive) {
        if (locomotive == null) {
            throw new IllegalArgumentException("Locomotive cannot be null");
        }
        if (locomotive.getNumber() == null) {
            throw new InMemoryNotNullConstraintException("Locomotive.number");
        }
        if (locomotive.getModel() == null) {
            throw new InMemoryNotNullConstraintException("Locomotive.model");
        }
    }
}
