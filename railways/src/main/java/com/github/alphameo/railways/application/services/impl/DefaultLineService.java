package com.github.alphameo.railways.application.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.mappers.LineMapper;
import com.github.alphameo.railways.application.mappers.StationMapper;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DefaultLineService implements LineService {

    private LineRepository lineRepo;
    private StationRepository stationRepo;

    @Override
    public void declareLine(@NonNull final LineDto line) {
        try {
            final var varLine = LineMapper.toEntity(line);
            lineRepo.create(varLine);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<StationDto> listStationsOfLine(@NonNull final String lineId) {
        try {
            final var valId = Id.fromString(lineId);
            final List<Station> stations = new ArrayList<>();
            final var stationIds = lineRepo.findById(valId).get().getStationIdOrder();
            for (final var id : stationIds) {
                stations.add(stationRepo.findById(id).get());
            }
            return StationMapper.toDtoList(stations);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LineDto findLineById(@NonNull final String id) {
        final Optional<Line> line;
        try {
            final var valId = Id.fromString(id);
            line = lineRepo.findById(valId);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (line.isEmpty()) {
            throw new EntityNotFoundException("Line", id.toString());
        }

        return LineMapper.toDto(line.get());
    }

    @Override
    public List<LineDto> listAllLines() {
        try {
            return LineMapper.toDtoList(lineRepo.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void disbandLine(@NonNull final String lineId) {
        try {
            final var valId = Id.fromString(lineId);
            lineRepo.deleteById(valId);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
}
