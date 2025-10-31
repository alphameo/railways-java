package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryUniqueCounstraintException;

import lombok.NonNull;

public class InMemoryLocomotiveRepository implements LocomotiveRepository {

    private final Map<Id, Locomotive> storage;
    private final HashMap<MachineNumber, Id> uniqueNumberIds = new HashMap<>();

    public InMemoryLocomotiveRepository(@NonNull final Map<Id, Locomotive> storage) {
        this.storage = storage;
    }

    @Override
    public void create(@NonNull final Locomotive locomotive) {
        final var number = locomotive.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryUniqueCounstraintException("Locomotive.number");
        }

        final var id = locomotive.getId();
        if (storage.containsKey(id)) {
            throw new InMemoryEntityAlreadyExistsException("Locomotive", id);
        }

        uniqueNumberIds.put(number, id);
        storage.put(id, locomotive);
    }

    @Override
    public Optional<Locomotive> findById(@NonNull final Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Locomotive> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Locomotive locomotive) {
        final var id = locomotive.getId();
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(locomotive.getClass().toString(), id);
        }
        final var number = locomotive.getNumber();
        final var oldNumber = storage.get(id).getNumber();
        if (oldNumber != number) {
            if (uniqueNumberIds.containsKey(number)) {
                throw new InMemoryUniqueCounstraintException("Locomotive.number");
            }
            uniqueNumberIds.remove(oldNumber);
            uniqueNumberIds.put(number, locomotive.getId());
        }

        storage.put(id, locomotive);
    }

    @Override
    public void deleteById(@NonNull final Id id) {
        final var delCandidate = storage.remove(id);
        if (delCandidate != null) {
            uniqueNumberIds.remove(delCandidate.getNumber());
        }
    }

    @Override
    public Optional<Locomotive> findByNumber(@NonNull final MachineNumber number) {
        final var id = uniqueNumberIds.get(number);
        final var locomotive = storage.get(id);
        return Optional.ofNullable(locomotive);
    }
}
