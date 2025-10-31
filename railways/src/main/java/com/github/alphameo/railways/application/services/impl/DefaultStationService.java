package com.github.alphameo.railways.application.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.mapper.StationMapper;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DefaultStationService implements StationService {

    private StationRepository stationRepo;

    @Override
    public void register(@NonNull final StationDto station) {
        try {
            final var valStation = StationMapper.toEntity(station);
            stationRepo.create(valStation);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public StationDto findById(@NonNull final Id id) {
        final Optional<Station> out;
        try {
            out = stationRepo.findById(id);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Station", id.toString());
        }

        return StationMapper.toDto(out.get());
    }

    @Override
    public List<StationDto> listAll() {
        try {
            return StationMapper.toDtoList(stationRepo.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unregister(final Id id) {
        try {
            stationRepo.deleteById(id);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
