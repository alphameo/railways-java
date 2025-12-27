package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryUniqueCounstraintException;

import lombok.NonNull;

public class InMemoryCarriageRepository implements CarriageRepository {

    private final Map<Id, Carriage> storage;
    private final Map<MachineNumber, Id> uniqueNumberIds = new HashMap<>();

    public InMemoryCarriageRepository(@NonNull final Map<Id, Carriage> storage) {
        this.storage = storage;
    }

    @Override
    public void create(@NonNull final Carriage carriage) {
        final var number = carriage.getNumber();
        if (uniqueNumberIds.containsKey(number)) {
            throw new InMemoryUniqueCounstraintException("Carriage.number");
        }

        final var id = carriage.getId();
        if (storage.containsKey(id)) {
            throw new InMemoryEntityAlreadyExistsException("Carriage", id);
        }

        uniqueNumberIds.put(carriage.getNumber(), id);
        storage.put(id, carriage);
    }

    @Override
    public Optional<Carriage> findById(@NonNull final Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Carriage> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Carriage carriage) {
        final var id = carriage.getId();
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

        storage.put(id, carriage);
    }

    @Override
    public void deleteById(@NonNull final Id id) {
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

    @Override
    public List<Carriage> listCarriages(int offset, int limit) {
        List<Carriage> list = new ArrayList<>(storage.values());
        // TODO: sort by number
        int start = Math.min(offset, list.size());
        int end = Math.min(offset + limit, list.size());
        return list.subList(start, end);
    }

    @Override
    public int countCarriages() {
        return storage.size();
    }
}
