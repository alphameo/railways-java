package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;

public class StationMapper {

    public static StationDto toDto(final Station station) {
        final var id = station.getId().toString();
        final var name = station.getName().getValue();
        final var location = station.getLocation().getValue();
        return new StationDto(
                id,
                name,
                location);
    }

    public static List<StationDto> toDtoList(final Iterable<Station> stations) {
        final var stationDtos = new ArrayList<StationDto>();
        for (final var station : stations) {
            stationDtos.add(toDto(station));
        }
        return stationDtos;
    }

    public static Station toEntity(final StationDto stationDto) {
        var id = Id.fromString(stationDto.id());
        if (id == null) {
            id = new Id();
        }
        final var name = new ObjectName(stationDto.name());
        final var location = new StationLocation(stationDto.location());
        return new Station(id, name, location);
    }

    public static Iterable<Station> toEntityList(final Iterable<StationDto> stationDtos) {
        final var stations = new ArrayList<Station>();
        for (final var stationDto : stationDtos) {
            stations.add(toEntity(stationDto));
        }
        return stations;
    }
}
