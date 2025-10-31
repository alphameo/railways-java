package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.valueobjects.CarriageContentType;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;

public class CarriageMapper {

    public static CarriageDto toDto(final Carriage carriage) {
        final var id = carriage.getId();
        final var number = carriage.getNumber().getValue();
        final var contentTypeVO = carriage.getContentType();
        String contentType;
        if (contentTypeVO == null) {
            contentType = null;
        } else {
            contentType = contentTypeVO.name();
        }
        final var capacity = carriage.getCapacity();
        return new CarriageDto(
                id,
                number,
                contentType,
                capacity);
    }

    public static List<CarriageDto> toDtoList(final Iterable<Carriage> carriages) {
        final var carriageDtos = new ArrayList<CarriageDto>();
        for (final var carriage : carriages) {
            carriageDtos.add(toDto(carriage));
        }
        return carriageDtos;
    }

    public static Carriage toEntity(final CarriageDto carriageDto) {
        var id = carriageDto.id();
        if (id == null) {
            id = new Id();
        }
        final var number = new MachineNumber(carriageDto.number());
        final var contentType = CarriageContentType.create(carriageDto.contentType());
        final var capacity = carriageDto.capacity();
        return new Carriage(id, number, contentType, capacity);
    }

    public static Iterable<Carriage> toEntityList(final Iterable<CarriageDto> carriageDtos) {
        final var carriages = new ArrayList<Carriage>();
        for (final var carriageDto : carriageDtos) {
            carriages.add(toEntity(carriageDto));
        }
        return carriages;
    }
}
