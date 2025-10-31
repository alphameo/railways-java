package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;

import lombok.NonNull;

public class InMemoryStationRepository implements StationRepository {

    private final Map<Id, Station> storage;

    public InMemoryStationRepository(@NonNull final Map<Id, Station> storage) {
        this.storage = storage;
    }

    @Override
    public void create(@NonNull final Station station) {
        final var id = station.getId();
        if (storage.containsKey(id)) {
            throw new InMemoryEntityAlreadyExistsException("Station", id);
        }

        storage.put(id, station);
    }

    @Override
    public Optional<Station> findById(@NonNull final Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Station> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Station station) {
        final var id = station.getId();
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(station.getClass().toString(), id);
        }

        storage.put(id, station);
    }

    @Override
    public void deleteById(@NonNull final Id id) {
        storage.remove(id);
    }
}
