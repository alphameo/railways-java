package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Station {

    private Long id;
    private ObjectName name;
    private StationLocation location;

    public Station(final Long id, final ObjectName name, final StationLocation location) {
        this.id = id;
        this.setName(name);
        this.setLocation(location);
    }

    public Station(Station station) {
        new Station(
                station.id,
                station.name,
                station.location);
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
}
