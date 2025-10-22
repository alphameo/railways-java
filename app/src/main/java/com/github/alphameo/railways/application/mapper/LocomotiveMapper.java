package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.valueobjects.LocomotiveModel;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;

public class LocomotiveMapper {

    public static LocomotiveDto toDto(final Locomotive locomotive) {
        final var number = locomotive.getNumber().getValue();
        final var model = locomotive.getModel().getValue();
        return new LocomotiveDto(
                number,
                model);

    }

    public static List<LocomotiveDto> toDtoList(final Iterable<Locomotive> locomotives) {
        final var locomotiveDtis = new ArrayList<LocomotiveDto>();
        for (final var locomotive : locomotives) {
            locomotiveDtis.add(toDto(locomotive));
        }
        return locomotiveDtis;
    }

    public static Locomotive toEntity(final LocomotiveDto locomotiveDto) {
        final var number = new MachineNumber(locomotiveDto.number());
        final var model = new LocomotiveModel(locomotiveDto.model());
        return new Locomotive(null, number, model);
    }

    public static Iterable<Locomotive> toEntityList(final Iterable<LocomotiveDto> LocomotiveDtos) {
        final var locomotives = new ArrayList<Locomotive>();
        for (final var locomotiveDto : LocomotiveDtos) {
            locomotives.add(toEntity(locomotiveDto));
        }
        return locomotives;
    }
}
