package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Line {

    private Long id;
    private String name;

    public Line(final Long id, final String name) {
        this.id = id;
        this.setName(name);
    }

    public void setName(final String name) {
        if (name == null) {
            throw new ValidationException("Line.name cannot be null");
        }

        this.name = name;
    }
}
