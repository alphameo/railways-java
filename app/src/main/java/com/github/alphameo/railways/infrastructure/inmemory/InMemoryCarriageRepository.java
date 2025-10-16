package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryUniqueCounstraintException;

import lombok.NonNull;

public class InMemoryCarriageRepository implements CarriageRepository {

    private final Map<Long, Carriage> storage;
    private Long idGenerator = 0L;
    private final Map<MachineNumber, Long> uniqueNumberIds = new HashMap<>();

    public InMemoryCarriageRepository(@NonNull final Map<Long, Carriage> storage) {
        this.storage = storage;
    }

    @Override
    public void create(@NonNull final Carriage carriage) {
        final var number = carriage.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryUniqueCounstraintException("Carriage.number");
        }

        Long id = carriage.getId();
        if (id == null) {
            id = ++idGenerator;
        } else {
            if (storage.containsKey(id)) {
                throw new InMemoryEntityAlreadyExistsException("Carriage", id);
            }
        }

        final var newCarriage = createCarriage(id, carriage);
        uniqueNumberIds.put(newCarriage.getNumber(), id);
        storage.put(id, newCarriage);
    }

    @Override
    public Optional<Carriage> findById(@NonNull final Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Carriage> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Carriage carriage) {
        final var id = carriage.getId();
        if (id == null) {
            throw new InMemoryException("id cannot be null");
        }
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(carriage.getClass().toString(), id);
        }
        final var number = carriage.getNumber();
        final var oldNumber = storage.get(id).getNumber();
        if (oldNumber != number) {
            if (uniqueNumberIds.containsKey(number)) {
                throw new InMemoryUniqueCounstraintException("Carriage.number");
            }
            uniqueNumberIds.remove(oldNumber);
            uniqueNumberIds.put(number, id);
        }

        final var newCarriage = createCarriage(id, carriage);
        storage.put(id, newCarriage);
    }

    @Override
    public void deleteById(@NonNull final Long id) {
        final var delCandidate = storage.remove(id);
        if (delCandidate != null) {
            uniqueNumberIds.remove(delCandidate.getNumber());
        }
    }

    @Override
    public Optional<Carriage> findByNumber(@NonNull final MachineNumber number) {
        final var id = uniqueNumberIds.get(number);
        final var carriage = storage.get(id);
        return Optional.ofNullable(carriage);
    }

    private static Carriage createCarriage(final long id, Carriage c) {
        return new Carriage(
                id,
                c.getNumber(),
                c.getContentType(),
                c.getCapacity());
    }
}
