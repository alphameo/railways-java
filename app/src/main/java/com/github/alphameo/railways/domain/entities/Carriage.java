package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Carriage {

    public enum ContentType {
        PASSENGER,
        CARGO;
    }

    private Long id;
    private String number;
    private ContentType contentType;
    private Long capacity;

    public Carriage(final Long id, final String number, final ContentType contentType, final Long capacity) {
        this.id = id;
        this.setNumber(number);
        this.contentType = contentType;
        this.setCapacity(capacity);
    }

    public void setNumber(final String number) {
        if (number == null) {
            throw new ValidationException("Carriage.number cannot be null");
        }

        this.number = number;
    }

    public void setCapacity(final Long capacity) {
        if (capacity < 0) {
            throw new ValidationException("Carriage.capacity must be >= 0");
        }

        this.capacity = capacity;
    }
}
