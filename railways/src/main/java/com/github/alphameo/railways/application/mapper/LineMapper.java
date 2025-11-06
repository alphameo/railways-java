package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;

public class LineMapper {

    public static LineDto toDto(final Line line) {
        final var id = line.getId().toString();
        final var name = line.getName().getValue();
        final var sIds = line.getStationIdOrder();
        final var stationIds = new ArrayList<String>();
        for (final var sId : sIds) {
            stationIds.add(sId.toString());
        }
        return new LineDto(
                id,
                name,
                stationIds);

    }

    public static List<LineDto> toDtoList(final Iterable<Line> lines) {
        final var lineDtos = new ArrayList<LineDto>();
        for (final var line : lines) {
            lineDtos.add(toDto(line));
        }
        return lineDtos;
    }

    public static Line toEntity(final LineDto lineDto) {
        var id = Id.fromString(lineDto.id());
        if (id == null) {
            id = new Id();
        }
        final var name = new ObjectName(lineDto.name());
        final var sIds = lineDto.stationIdOrder();
        final var stationIds = new ArrayList<Id>();
        for (final var sId : sIds) {
            stationIds.add(Id.fromString(sId));
        }
        return new Line(id, name, stationIds);
    }

    public static Iterable<Line> toEntityList(final Iterable<LineDto> lineDtos) {
        final var lines = new ArrayList<Line>();
        for (final var lineDto : lineDtos) {
            lines.add(toEntity(lineDto));
        }
        return lines;
    }
}
