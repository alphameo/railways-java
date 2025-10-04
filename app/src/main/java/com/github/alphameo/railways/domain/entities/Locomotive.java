package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Locomotive {

    private Long id;
    private String number;
    private String model;

    public Locomotive(final Long id, final String number, final String model) {
        this.id = id;
        this.setNumber(number);
        this.setModel(model);
    }

    public void setNumber(final String number) {
        if (number == null) {
            throw new ValidationException("Locomotive.number cannot be null");
        }

        this.number = number;
    }

    public void setModel(final String model) {
        if (model == null) {
            throw new ValidationException("Locomotive.model cannot be null");
        }

        this.model = model;
    }
}
