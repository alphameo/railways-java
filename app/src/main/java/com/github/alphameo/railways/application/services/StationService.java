package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

public class StationService {

    private StationRepository repository;

    public Station register(final String name, final String location) {
        try {
            final var station = new Station(null, name, location);
            return repository.create(station);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Station findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        final Optional<Station> out;
        try {
            out = repository.findById(id);
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
            return repository.findAll();
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void unregister(Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
