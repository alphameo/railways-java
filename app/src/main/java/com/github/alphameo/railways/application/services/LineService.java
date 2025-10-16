package com.github.alphameo.railways.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.NonNull;

public class LineService {

    private LineRepository lineRepo;
    private StationRepository stationRepo;

    public void declareLine(final ObjectName name, final List<Long> stations) {
        try {
            final var line = new Line(null, name, stations);
            lineRepo.create(line);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Station> listLineStations(@NonNull final Long lineId) {
        try {
            final List<Station> stations = new ArrayList<>();
            final var stationIds = lineRepo.findById(lineId).get().getStationIds();
            for (long id : stationIds) {
                stations.add(stationRepo.findById(id).get());
            }
            return stations;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Line findById(@NonNull final Long id) {
        final Optional<Line> out;
        try {
            out = lineRepo.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Line", id.toString());
        }

        return out.get();
    }

    public List<Line> listAll() {
        try {
            return lineRepo.findAll();
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void disbandLine(@NonNull final Long lineId) {
        try {
            lineRepo.deleteById(lineId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
