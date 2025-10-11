package com.github.alphameo.railways.domain.entities;

import java.util.HashSet;
import java.util.Set;

import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Station {

    private Long id;
    private ObjectName name;
    private StationLocation location;
    private Set<Long> lineIds;

    public Station(final Long id, final ObjectName name, final StationLocation location,
            final Set<Long> lineIds) {
        this.id = id;
        this.setName(name);
        this.setLocation(location);
        this.setLineIds(lineIds);
    }

    public Station(Station station) {
        new Station(
                station.id,
                station.name,
                station.location,
                station.lineIds);
    }

    public void setName(final ObjectName name) {
        if (name == null) {
            throw new ValidationException("Station.name cannot be null");
        }

        this.name = name;
    }

    public void setLocation(final StationLocation location) {
        if (location == null) {
            throw new ValidationException("Station.location cannot be null");
        }

        this.location = location;
    }

    public void setLineIds(Set<Long> lineIds) {
        if (lineIds == null) {
            this.lineIds = new HashSet<>();
        }

        final var newIds = new HashSet<Long>();

        for (Long id : lineIds) {
            if (id == null) {
                throw new ValidationException("lineId cannot be null");
            }
            newIds.add(id);
        }

        this.lineIds = lineIds;
    }

    public Set<Long> getLineIds() {
        return new HashSet<>(this.lineIds);
    }

    public void connectToLine(Long lineId) {
        this.lineIds.add(lineId);
    }

    public void disconnectFromLine(Long lineId) {
        this.lineIds.remove(lineId);
    }

    public int getStationCount() {
        return this.lineIds.size();
    }
}
