package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryException;

import lombok.NonNull;

public class InMemoryStationRepository implements StationRepository {

    private final Map<Long, Station> storage = new HashMap<>();
    private Long idGenerator = 0L;

    @Override
    public void create(@NonNull final Station station) {
        Long id = station.getId();
        if (station.getId() == null) {
            id = ++idGenerator;
        } else {
            if (storage.containsKey(id)) {
                throw new InMemoryEntityAlreadyExistsException("Station", id);
            }
        }

        final var newStation = createStation(id, station);
        storage.put(id, newStation);
    }

    @Override
    public Optional<Station> findById(@NonNull final Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Station> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Station station) {
        final var id = station.getId();
        if (id == null) {
            throw new InMemoryException("id cannot be null");
        }
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(station.getClass().toString(), id);
        }

        final var newStation = createStation(id, station);
        storage.put(id, newStation);
    }

    @Override
    public void deleteById(@NonNull final Long id) {
        storage.remove(id);
    }

    private static Station createStation(final long id, Station s) {
        return new Station(
                id,
                s.getName(),
                s.getLocation());
    }
}
