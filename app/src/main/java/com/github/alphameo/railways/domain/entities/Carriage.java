package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.domain.valueobjects.CarriageContentType;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Carriage {

    private Long id;
    private MachineNumber number;
    private CarriageContentType contentType;
    private Long capacity;

    public Carriage(final Long id, final MachineNumber number,
            final CarriageContentType contentType, final Long capacity) {
        this.id = id;
        this.setNumber(number);
        this.contentType = contentType;
        this.setCapacity(capacity);
    }

    public Carriage(final Carriage carriage) {
        new Carriage(
                carriage.id,
                carriage.number,
                carriage.contentType,
                carriage.capacity);
    }

    public void setNumber(final MachineNumber number) {
        if (number == null) {
            throw new ValidationException("Carriage.number cannot be null");
        }

        this.number = number;
    }

    public void setCapacity(final Long capacity) {
        if (capacity == null) {
            throw new ValidationException("Carriage.capacity cannot be null");
        }
        if (capacity <= 0) {
            throw new ValidationException("Carriage.capacity must be > 0");
        }

        this.capacity = capacity;
    }
}
