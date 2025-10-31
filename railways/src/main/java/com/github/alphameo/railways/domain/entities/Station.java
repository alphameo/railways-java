package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Station {

    private Id id;
    private ObjectName name;
    private StationLocation location;

    public Station(final Id id, final ObjectName name, final StationLocation location) {
        if (id == null) {
            throw new ValidationException("Station.id cannot be null");
        }
        this.changeName(name);
        this.changeLocation(location);
    }

    public Station(final ObjectName name, final StationLocation location) {
        this(new Id(), name, location);
    }

    public Station(Station station) {
        new Station(
                station.id,
                station.name,
                station.location);
    }

    public void changeName(final ObjectName name) {
        if (name == null) {
            throw new ValidationException("Station.name cannot be null");
        }

        this.name = name;
    }

    public void changeLocation(final StationLocation location) {
        if (location == null) {
            throw new ValidationException("Station.location cannot be null");
        }

        this.location = location;
    }
}
