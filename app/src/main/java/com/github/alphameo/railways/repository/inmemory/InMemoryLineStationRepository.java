package com.github.alphameo.railways.repository.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.LineStation;
import com.github.alphameo.railways.domain.LineStation.LineStationId;
import com.github.alphameo.railways.repository.LineStationRepository;

public class InMemoryLineStationRepository implements LineStationRepository {

    private final InMemoryStorage<LineStation, LineStationId> storage = new InMemoryStorage<>();

    @Override
    public LineStation add(LineStation lineStaion)
            throws IllegalArgumentException {
        if (lineStaion == null) {
            throw new IllegalArgumentException("Invalid lineStaion: object is null");
        }
        validate(lineStaion);
        if (lineStaion.getId() == null) {
            throw new IllegalArgumentException("Invalid lineStaion: id is null");
        }

        storage.add(lineStaion.getId(), lineStaion);
        return lineStaion;
    }

    @Override
    public Optional<LineStation> findById(LineStationId id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<LineStation> findAll() {
        return storage.findAll();
    }

    @Override
    public boolean update(LineStation lineStaion) throws IllegalArgumentException {
        if (lineStaion == null) {
            throw new IllegalArgumentException("Invalid lineStaion: object is null");
        }
        validate(lineStaion);

        return storage.update(lineStaion.getId(), lineStaion);
    }

    @Override
    public boolean deleteById(LineStationId id) throws IllegalArgumentException {
        return storage.deleteById(id) != null;
    }

    public void validate(LineStation lineStaion) {
        if (lineStaion.getLineId() == null) {
            throw new IllegalArgumentException("Invalid lineStaion: lineId is null");
        }
        if (lineStaion.getStationId() == null) {
            throw new IllegalArgumentException("Invalid lineStaion: stationId is null");
        }
        if (lineStaion.getPosition() == null) {
            throw new IllegalArgumentException("Invalid lineStaion: position is null");
        }
        if (lineStaion.getPosition() < 0) {
            throw new IllegalArgumentException("Invalid lineStaion: position < 0");
        }
    }
}
