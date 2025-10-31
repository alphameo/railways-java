package com.github.alphameo.railways.application.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.mapper.LocomotiveMapper;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DefaultLocomotiveService implements LocomotiveService {

    private LocomotiveRepository repository;

    @Override
    public void register(@NonNull final LocomotiveDto locomotive) {
        try {
            final var valLocomotive = LocomotiveMapper.toEntity(locomotive);
            repository.create(valLocomotive);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LocomotiveDto findById(@NonNull final Id id) {
        final Optional<Locomotive> out;
        try {
            out = repository.findById(id);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Locomotive", id.toString());
        }

        return LocomotiveMapper.toDto(out.get());
    }

    @Override
    public LocomotiveDto findByNumber(@NonNull final String number) {
        final Optional<Locomotive> out;
        try {
            final var valNumber = new MachineNumber(number);
            out = repository.findByNumber(valNumber);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException(String.format("Locomotive with number=%s not exists", number));
        }

        return LocomotiveMapper.toDto(out.get());
    }

    @Override
    public List<LocomotiveDto> listAll() {
        try {
            return LocomotiveMapper.toDtoList(repository.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unregister(@NonNull final Id id) {
        try {
            repository.deleteById(id);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
