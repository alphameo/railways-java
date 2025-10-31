package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.valueobjects.Id;

public class TrainCompositionMapper {

    public static TrainCompositionDto toDto(final TrainComposition trainComposition) {
        final var id = trainComposition.getId();
        final var locomotiveId = trainComposition.getLocomotiveId();
        final var carriageIds = trainComposition.getCarriageIds();
        return new TrainCompositionDto(
                id,
                locomotiveId,
                carriageIds);
    }

    public static List<TrainCompositionDto> toDtoList(Iterable<TrainComposition> trainComposiotions) {
        final var trainCompositionDtos = new ArrayList<TrainCompositionDto>();
        for (final var trainComposition : trainComposiotions) {
            trainCompositionDtos.add(toDto(trainComposition));
        }
        return trainCompositionDtos;
    }

    public static TrainComposition toEntity(final TrainCompositionDto trainCompositionDto) {
        var id = trainCompositionDto.id();
        if (id == null) {
            id = new Id();
        }
        final var locomotiveId = trainCompositionDto.locomotiveId();
        final var carriageIds = trainCompositionDto.carriageIds();
        return new TrainComposition(id, locomotiveId, carriageIds);
    }

    public static Iterable<TrainComposition> toEntityList(final Iterable<TrainCompositionDto> trainCompositionDtos) {
        final var trainCompositions = new ArrayList<TrainComposition>();
        for (final var trainComposition : trainCompositionDtos) {
            trainCompositions.add(toEntity(trainComposition));
        }
        return trainCompositions;
    }
}
