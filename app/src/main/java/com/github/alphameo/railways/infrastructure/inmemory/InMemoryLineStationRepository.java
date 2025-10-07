package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.LineStation;
import com.github.alphameo.railways.domain.entities.LineStation.LineStationId;
import com.github.alphameo.railways.domain.repositories.LineStationRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryConstraintException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;

public class InMemoryLineStationRepository implements LineStationRepository {

    private final InMemoryStorage<LineStation, LineStationId> storage = new InMemoryStorage<>();

    @Override
    public LineStation create(final LineStation lineStaion) {
        validate(lineStaion);

        return storage.create(lineStaion.getId(), lineStaion);
    }

    @Override
    public Optional<LineStation> findById(final LineStationId id) {
        validateId(id);

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
        validateId(id);

        storage.deleteById(id);
    }

    @Override
    public List<Long> findLineIdsByStationId(Long stationId) {
        final var lineIds = new ArrayList<Long>();
        for (final var lineStation : storage.findAll()) {
            final var lsId = lineStation.getId();
            if (stationId.equals(lsId.getStationId())) {
                lineIds.add(lsId.getLineId());
            }
        }
        return lineIds;
    }

    @Override
    public List<Long> findStationsByLineId(Long lineId) {
        final var stationIds = new ArrayList<Long>();
        for (final var lineStation : storage.findAll()) {
            final var lsId = lineStation.getId();
            if (lineId.equals(lsId.getLineId())) {
                stationIds.add(lsId.getStationId());
            }
        }
        return stationIds;
    }

    private static void validateId(LineStationId id) {
        if (id == null) {
            throw new InMemoryNotNullConstraintException("LineStaionId");
        }
        if (id.getLineId() == null) {
            throw new InMemoryNotNullConstraintException("LineStaionId.lineId");
        }
        if (id.getStationId() == null) {
            throw new InMemoryNotNullConstraintException("LineStaionId.stationId");
        }
    }

    private static void validate(final LineStation lineStaion) {
        if (lineStaion == null) {
            throw new InMemoryNotNullConstraintException("LineStaion");
        }
        validateId(lineStaion.getId());
        if (lineStaion.getPosition() == null) {
            throw new InMemoryNotNullConstraintException("LineStaion.position");
        }
        if (lineStaion.getPosition() < 0) {
            throw new InMemoryConstraintException("LineStaion.position", ">= 0");
        }
    }
}
