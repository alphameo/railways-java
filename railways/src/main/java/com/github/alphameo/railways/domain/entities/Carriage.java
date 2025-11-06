package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.domain.valueobjects.CarriageContentType;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Carriage {

    private static int MIN_CAPACITY = 0;

    private Id id;
    private MachineNumber number;
    private CarriageContentType contentType;
    private Long capacity;

    public Carriage(final Id id, final MachineNumber number) {
        if (id == null) {
            throw new ValidationException("carriage: id cannot be null");
        }
        this.id = id;
        this.changeNumber(number);
    }

    public Carriage(final MachineNumber number) {
        this(new Id(), number);
    }

    public void changeNumber(final MachineNumber number) {
        if (number == null) {
            throw new ValidationException("carriage: number cannot be null");
        }

        this.number = number;
    }

    public void changeCapacity(final Long capacity) {
        if (capacity == null) {
            this.capacity = null;
            return;
        }
        if (capacity <= MIN_CAPACITY) {
            throw new ValidationException(String.format("carriage: capacity must be > %s", MIN_CAPACITY));
        }

        this.capacity = capacity;
    }

    public void changeContentType(final CarriageContentType contentType) {
        this.contentType = contentType;
    }
}
