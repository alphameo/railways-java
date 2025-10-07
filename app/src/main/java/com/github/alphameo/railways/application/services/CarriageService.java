package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.entities.Carriage.ContentType;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarriageService {

    private CarriageRepository repository;

    public Carriage register(final String number, final ContentType contentType, final Long capacity) {
        try {
            final var carriage = new Carriage(null, number, contentType, capacity);
            return repository.create(carriage);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Carriage findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        final Optional<Carriage> out;
        try {
            out = repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Carriage", id.toString());
        }

        return out.get();
    }

    public List<Carriage> listAll() {
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
