package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.mapper.LocomotiveMapper;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class LocomotiveService {

    private LocomotiveRepository repository;

    public void register(@NonNull final LocomotiveDto locomotive) {
        try {
            final var valLocomotive = LocomotiveMapper.toEntity(locomotive);
            repository.create(valLocomotive);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    public LocomotiveDto findById(@NonNull final Long id) {
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

    public LocomotiveDto findByNumber(@NonNull String number) {
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
    public List<LocomotiveDto> listAll() {
        try {
            return LocomotiveMapper.toDtoList(repository.findAll());
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    public void unregister(@NonNull final Long id) {
        try {
            repository.deleteById(id);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
