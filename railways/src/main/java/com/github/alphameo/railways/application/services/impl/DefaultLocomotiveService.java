package com.github.alphameo.railways.application.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.mappers.LocomotiveMapper;
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
    public void registerLocomotive(@NonNull final LocomotiveDto locomotive) {
        try {
            final var valLocomotive = LocomotiveMapper.toEntity(locomotive);
            repository.create(valLocomotive);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LocomotiveDto findLocomotiveById(@NonNull final String id) {
        final Optional<Locomotive> out;
        try {
            final var valId = Id.fromString(id);
            out = repository.findById(valId);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (out.isEmpty()) {
            throw new EntityNotFoundException("Locomotive", id.toString());
        }

        return LocomotiveMapper.toDto(out.get());
    }

    @Override
    public LocomotiveDto findLocomotiveByNumber(@NonNull final String number) {
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
    public List<LocomotiveDto> listAllLocomotives() {
        try {
            return LocomotiveMapper.toDtoList(repository.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unregisterLocomotive(@NonNull final String id) {
        try {
            final var valId = Id.fromString(id);
            repository.deleteById(valId);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
