package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.LineStation;
import com.github.alphameo.railways.domain.entities.LineStation.LineStationId;
import com.github.alphameo.railways.domain.repositories.LineStationRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryLineStationRepository implements LineStationRepository {

    private final InMemoryStorage<LineStation, LineStationId> storage = new InMemoryStorage<>();

    @Override
    public LineStation create(final LineStation lineStaion) {
        validate(lineStaion);

        return storage.create(lineStaion.getId(), lineStaion);
    }

    @Override
    public Optional<LineStation> findById(final LineStationId id) {
        if (id == null) {
            throw new InMemoryException("LineStaionId cannot be null");
        }
        if (id.getLineId() == null) {
            throw new InMemoryException("LineStaionId.lineId cannot be null");
        }
        if (id.getStationId() == null) {
            throw new InMemoryException("LineStaionId.stationId cannot be null");
        }

        return storage.getById(id);
    }

    @Override
    public List<LineStation> findAll() {
        return storage.findAll();
    }

    @Override
    public LineStation update(final LineStation lineStaion) {
        validate(lineStaion);

        return storage.update(lineStaion.getId(), lineStaion);
    }

    @Override
    public void deleteById(final LineStationId id) {
        if (id == null) {
            throw new InMemoryException("LineStaionId cannot be null");
        }
        if (id.getLineId() == null) {
            throw new InMemoryException("LineStaionId.lineId cannot be null");
        }
        if (id.getStationId() == null) {
            throw new InMemoryException("LineStaionId.stationId cannot be null");
        }

        storage.deleteById(id);
    }

    public void validate(final LineStation lineStaion) {
        if (lineStaion == null) {
            throw new InMemoryException("LineStaion cannot be null");
        }
        if (lineStaion.getLineId() == null) {
            throw new InMemoryException("LineStaion.lineId cannot be null");
        }
        if (lineStaion.getStationId() == null) {
            throw new InMemoryException("LineStaion.stationId cannot be null");
        }
        if (lineStaion.getPosition() == null) {
            throw new InMemoryException("LineStaion.position cannot be null");
        }
        if (lineStaion.getPosition() < 0) {
            throw new InMemoryException("LineStaion.position should be >= 0");
        }
    }
}
