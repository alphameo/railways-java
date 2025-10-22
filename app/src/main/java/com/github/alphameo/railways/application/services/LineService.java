package com.github.alphameo.railways.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.mapper.LineMapper;
import com.github.alphameo.railways.application.mapper.StationMapper;
import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.NonNull;

public class LineService {

    private LineRepository lineRepo;
    private StationRepository stationRepo;

    public void declareLine(@NonNull final LineDto line) {
        try {
            final var varLine = LineMapper.toEntity(line);
            lineRepo.create(varLine);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<StationDto> listLineStations(@NonNull final Long lineId) {
        try {
            final List<Station> stations = new ArrayList<>();
            final var stationIds = lineRepo.findById(lineId).get().getStationIds();
            for (final long id : stationIds) {
                stations.add(stationRepo.findById(id).get());
            }
            return StationMapper.toDtoList(stations);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public LineDto findById(@NonNull final Long id) {
        final Optional<Line> line;
        try {
            line = lineRepo.findById(id);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (line.isEmpty()) {
            throw new EntityNotFoundException("Line", id.toString());
        }

        return LineMapper.toDto(line.get());
    }

    public List<LineDto> listAll() {
        try {
            return LineMapper.toDtoList(lineRepo.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void disbandLine(@NonNull final Long lineId) {
        try {
            lineRepo.deleteById(lineId);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
