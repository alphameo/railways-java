package com.github.alphameo.railways.application.services;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.mapper.CarriageMapper;
import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.application.services.EntityNotFoundException;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CarriageService {

    private final CarriageRepository repository;

    public void register(@NonNull final CarriageDto carriage) {
        try {
            final var valCarriage = CarriageMapper.toEntity(carriage);
            repository.create(valCarriage);
        } catch (final RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    public CarriageDto findById(@NonNull final Long id) {
        final Optional<Carriage> carr;
        try {
            carr = repository.findById(id);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        if (carr.isEmpty()) {
            throw new EntityNotFoundException("Carriage", id.toString());
        }

        return CarriageMapper.toDto(carr.get());
    }

    public CarriageDto findByNumber(@NonNull final String number) {
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

    public List<CarriageDto> listAll() {
        try {
            final var carriages =  repository.findAll();
            return CarriageMapper.toDtoList(carriages);
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
