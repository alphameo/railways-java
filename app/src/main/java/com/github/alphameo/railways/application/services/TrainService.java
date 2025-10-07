package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

public class TrainService {

    private TrainRepository repository;

    public Train register(final String number) {
        try {
            final var train = new Train(null, number);
            return repository.create(train);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Train findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        final Optional<Train> out;
        try {
            out = repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Train", id.toString());
        }

        return out.get();
    }

    public List<Train> listAll() {
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
