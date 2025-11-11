package com.github.alphameo.railways.application.mappers;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.valueobjects.Id;

public class TrainCompositionMapper {

    public static TrainCompositionDto toDto(final TrainComposition trainComposition) {
        final var id = trainComposition.getId().toString();
        final var locomotiveId = trainComposition.getLocomotiveId().toString();
        final var carriageIds = IdMapper.toStringList(trainComposition.getCarriageIds());
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
        final var locomotiveId = Id.fromString(trainCompositionDto.locomotiveId());
        final var carriageIds = IdMapper.toIdList(trainCompositionDto.carriageIds());

        final TrainComposition trainComposition;
        final var strId = trainCompositionDto.id();
        if (strId == null) {
            trainComposition = new TrainComposition(locomotiveId, carriageIds);
        } else {
            var id = Id.fromString(strId);
            trainComposition = new TrainComposition(id, locomotiveId, carriageIds);
        }

        return trainComposition;
    }

    public static Iterable<TrainComposition> toEntityList(final Iterable<TrainCompositionDto> trainCompositionDtos) {
        final var trainCompositions = new ArrayList<TrainComposition>();
        for (final var trainComposition : trainCompositionDtos) {
            trainCompositions.add(toEntity(trainComposition));
        }
        return trainCompositions;
    }
}
