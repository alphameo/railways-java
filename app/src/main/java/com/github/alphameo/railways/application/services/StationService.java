package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.NonNull;

public class StationService {

    private StationRepository stationRepo;

    public void register(final ObjectName name, final StationLocation location) {
        try {
            final var station = new Station(null, name, location);
            stationRepo.create(station);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Station findById(@NonNull final Long id) {
        final Optional<Station> out;
        try {
            out = stationRepo.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Station", id.toString());
        }

        return out.get();
    }

    public List<Station> listAll() {
        try {
            return stationRepo.findAll();
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void unregister(Long id) {
        try {
            stationRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
