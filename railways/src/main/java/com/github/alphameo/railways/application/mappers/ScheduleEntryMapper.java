package com.github.alphameo.railways.application.mappers;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.ScheduleEntry;

public class ScheduleEntryMapper {

    public static ScheduleEntryDto toDto(final ScheduleEntry scheduleEntry) {
        final var stationId = scheduleEntry.getStationId().toString();
        final var arrivalTime = scheduleEntry.getArrivalTime();
        final var departureTime = scheduleEntry.getDepartureTime();
        return new ScheduleEntryDto(
                stationId,
                arrivalTime,
                departureTime);
    }

    public static List<ScheduleEntryDto> toDtoList(final Iterable<ScheduleEntry> scheduleEntries) {
        final var scheduleEntryDtos = new ArrayList<ScheduleEntryDto>();
        for (final var scheduleEntry : scheduleEntries) {
            scheduleEntryDtos.add(toDto(scheduleEntry));
        }
        return scheduleEntryDtos;
    }

    public static ScheduleEntry toValueObject(final ScheduleEntryDto scheduleEntryDto) {
        final var stationId = Id.fromString(scheduleEntryDto.stationId());
        final var arrivalTime = scheduleEntryDto.arrivalTime();
        final var departureTime = scheduleEntryDto.departureTime();
        return new ScheduleEntry(stationId, arrivalTime, departureTime);
    }

    public static List<ScheduleEntry> toValueObjectList(final Iterable<ScheduleEntryDto> scheduleEntryDtos) {
        final var scheduleEntries = new ArrayList<ScheduleEntry>();
        for (final var scheduleEntryDto : scheduleEntryDtos) {
            scheduleEntries.add(toValueObject(scheduleEntryDto));
        }
        return scheduleEntries;
    }
}
