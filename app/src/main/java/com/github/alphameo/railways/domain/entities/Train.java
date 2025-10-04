package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Train {

    private Long id;
    private String number;

    public Train(final Long id, final String number) {
        this.id = id;
        this.setNumber(number);
    }

    public void setNumber(final String number) {
        if (number == null) {
            throw new ValidationException("Train.number cannot be null");
        }

        this.number = number;
    }
}
