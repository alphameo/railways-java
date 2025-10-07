package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

public class LocomotiveService {

    private LocomotiveRepository repository;

    public Locomotive register(final String number, final String model) {
        try {
            final var locomotive = new Locomotive(null, number, model);
            return repository.create(locomotive);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Locomotive findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        final Optional<Locomotive> out;
        try {
            out = repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Locomotive", id.toString());
        }

        return out.get();
    }

    public List<Locomotive> listAll() {
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
