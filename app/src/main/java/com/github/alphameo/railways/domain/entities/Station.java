package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Station {

    private Long id;
    private String name;
    private String location;

    public Station(final Long id, final String name, final String location) {
        this.id = id;
        this.setName(name);
        this.setLocation(location);
    }

    public void setName(final String name) {
        if (name == null) {
            throw new ValidationException("Station.name cannot be null");
        }

        this.name = name;
    }

    public void setLocation(final String location) {
        if (location == null) {
            throw new ValidationException("Station.location cannot be null");
        }

        this.location = location;
    }
}
