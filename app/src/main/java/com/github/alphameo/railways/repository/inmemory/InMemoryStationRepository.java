package com.github.alphameo.railways.repository.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.alphameo.railways.domain.Station;
import com.github.alphameo.railways.repository.StationRepository;

public class InMemoryStationRepository implements StationRepository {

    private final InMemoryStorage<Station, Long> storage = new InMemoryStorage<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Station add(Station station) throws IllegalArgumentException {
        if (station == null) {
            throw new IllegalArgumentException("Invalid station: object is null");
        }
        validate(station);
        if (station.getId() == null) {
            long id = idGenerator.incrementAndGet();
            station.setId(id);
        }

        storage.add(station.getId(), station);
        return station;
    }

    @Override
    public Optional<Station> findById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Station> findAll() {
        return storage.findAll();
    }

    @Override
    public boolean update(Station station) throws IllegalArgumentException {
        if (station == null) {
            throw new IllegalArgumentException("Invalid station: object is null");
        }
        validate(station);

        return storage.update(station.getId(), station);
    }

    @Override
    public boolean deleteById(Long id) throws IllegalArgumentException {
        return storage.deleteById(id) != null;
    }

    public void validate(Station station) throws IllegalArgumentException {
        if (station.getName() == null) {
            throw new IllegalArgumentException("Invalid station: name is null");
        }
        if (station.getLocation() == null) {
            throw new IllegalArgumentException("Invalid station: location is null");
        }
    }
}
