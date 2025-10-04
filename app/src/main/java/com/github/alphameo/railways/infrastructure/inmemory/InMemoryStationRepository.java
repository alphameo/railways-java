package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryStationRepository implements StationRepository {

    private final InMemoryStorage<Station, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public Station create(final Station station) {
        validate(station);
        if (station.getId() == null) {
            final long id = ++idGenerator;
            station.setId(id);
        }

        storage.create(station.getId(), station);
        return station;
    }

    @Override
    public Optional<Station> findById(final Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Station> findAll() {
        return storage.findAll();
    }

    @Override
    public Station update(final Station station) {
        validate(station);

        return storage.update(station.getId(), station);
    }

    @Override
    public void deleteById(final Long id) {
        storage.deleteById(id);
    }

    public void validate(final Station station) {
        if (station == null) {
            throw new InMemoryException("Station cannot be null");
        }
        if (station.getName() == null) {
            throw new InMemoryException("Station.name cannot be null");
        }
        if (station.getLocation() == null) {
            throw new InMemoryException("Station.location cannot be null");
        }
    }
}
