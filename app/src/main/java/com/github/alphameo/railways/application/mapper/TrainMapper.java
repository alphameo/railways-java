package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;

public class TrainMapper {

    public static TrainDto toDto(final Train train) {
        final var number = train.getNumber().getValue();
        final var trainCompositionId = train.getTrainCompositionId();
        final var schedule = train.getSchedule();
        final var scheduleDto = ScheduleEntryMapper.toDtoList(schedule);
        return new TrainDto(
                number,
                trainCompositionId,
                scheduleDto);
    }

    public static List<TrainDto> toDtoList(Iterable<Train> trains) {
        final var trainDtos = new ArrayList<TrainDto>();
        for (final var train : trains) {
            trainDtos.add(toDto(train));
        }
        return trainDtos;
    }

    public static Train toEntity(final TrainDto trainDto) {
        final var number = new MachineNumber(trainDto.number());
        final var trainCompositionId = trainDto.trainCompositionId();
        final var scheduleDto = trainDto.schedule();
        final var schedule = ScheduleEntryMapper.toValueObjectList(scheduleDto);
        return new Train(null, number, trainCompositionId, schedule);
    }

    public static Iterable<Train> toEntityList(final Iterable<TrainDto> trainDtos) {
        final var trains = new ArrayList<Train>();
        for (final var trainDto : trainDtos) {
            trains.add(toEntity(trainDto));
        }
        return trains;
    }
}
