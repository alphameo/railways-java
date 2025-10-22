package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.mapper.StationMapper;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.NonNull;

public class StationService {

    private StationRepository stationRepo;

    public void register(@NonNull final StationDto station) {
        try {
            final var name = new ObjectName(station.name());
            final var location = new StationLocation(station.location());
            final var valStation = new Station(null, name, location);
            stationRepo.create(valStation);
        } catch (final RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public StationDto findById(@NonNull final Long id) {
        final Optional<Station> out;
        try {
            out = stationRepo.findById(id);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Station", id.toString());
        }

        return StationMapper.toDto(out.get());
    }

    public List<StationDto> listAll() {
        try {
            return StationMapper.toDtoList(stationRepo.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void unregister(final Long id) {
        try {
            stationRepo.deleteById(id);
        } catch (final RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
