package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;

import lombok.NonNull;

public class InMemoryLineRepository implements LineRepository {

    private final Map<Id, Line> storage;
    private final Map<Id, Station> stationStorage;

    public InMemoryLineRepository(@NonNull final Map<Id, Line> storage,
            @NonNull final Map<Id, Station> stationStorage) {
        this.storage = storage;
        this.stationStorage = stationStorage;
    }

    @Override
    public void create(@NonNull final Line line) {
        validate(line);

        final var id = line.getId();
        if (storage.containsKey(id)) {
            throw new InMemoryEntityAlreadyExistsException("Line", id);
        }

        storage.put(id, line);
    }

    @Override
    public Optional<Line> findById(@NonNull final Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Line> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Line line) {
        validate(line);
        final var id = line.getId();
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(line.getClass().toString(), id);
        }

        storage.put(id, line);
    }

    @Override
    public void deleteById(@NonNull final Id id) {
        storage.remove(id);
    }

    private void validate(final Line line) {
        final var stationIds = line.getStationIdOrder();
        for (final Id id : stationIds) {
            if (stationStorage.get(id) == null) {
                throw new InMemoryEntityNotExistsException("Station", id);
            }
        }
    }
}
