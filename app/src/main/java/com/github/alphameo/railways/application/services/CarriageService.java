package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.valueobjects.CarriageContentType;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CarriageService {

    private CarriageRepository repository;

    public void register(final MachineNumber number, final CarriageContentType contentType, final Long capacity) {
        try {
            final var carriage = new Carriage(null, number, contentType, capacity);
            repository.create(carriage);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Carriage findById(@NonNull final Long id) {
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

    public Carriage findByNumber(@NonNull final MachineNumber number) {
        final Optional<Carriage> out;
        try {
            out = repository.findByNumber(number);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException(String.format("Carriage with number=%s not exists", number));
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

    public void unregister(@NonNull final Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
