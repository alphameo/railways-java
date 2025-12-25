package com.github.alphameo.railways.application.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.mappers.CarriageMapper;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DefaultCarriageService implements CarriageService {

    private final CarriageRepository repository;

    @Override
    public void registerCarriage(@NonNull final CarriageDto carriage) {
        try {
            final var id = carriage.id();
            if (id == null) {

            }
            final var valCarriage = CarriageMapper.toEntity(carriage);
            repository.create(valCarriage);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CarriageDto findCarriageById(@NonNull final String id) {
        final Optional<Carriage> carr;
        try {
            final var valId = Id.fromString(id);
            carr = repository.findById(valId);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (carr.isEmpty()) {
            throw new EntityNotFoundException("Carriage", id.toString());
        }

        return CarriageMapper.toDto(carr.get());
    }

    @Override
    public CarriageDto findCarriageByNumber(@NonNull final String number) {
        final Optional<Carriage> carr;
        try {
            final var valNumber = new MachineNumber(number);
            carr = repository.findByNumber(valNumber);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (carr.isEmpty()) {
            throw new EntityNotFoundException(String.format("Carriage with number=%s not exists", number));
        }

        return CarriageMapper.toDto(carr.get());
    }

    @Override
    public List<CarriageDto> listAllCarriages() {
        try {
            final var carriages = repository.findAll();
            return CarriageMapper.toDtoList(carriages);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCarriage(@NonNull final CarriageDto carriage) {
        try {
            final var valCarriage = CarriageMapper.toEntity(carriage);
            repository.update(valCarriage);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unregisterCarriage(@NonNull final String id) {
        try {
            final var valId = Id.fromString(id);
            repository.deleteById(valId);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
