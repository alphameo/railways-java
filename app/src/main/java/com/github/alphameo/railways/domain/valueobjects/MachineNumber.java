package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MachineNumber {

    private String value;

    public MachineNumber(final String number) {
        if (number == null) {
            throw new ValidationException("MachineNumber.value cannot be null");
        }

        var trimmedNumber = number.trim();

        if (trimmedNumber.isBlank()) {
            throw new ValidationException("MachineNumber.value cannot be blank");
        }

        if (trimmedNumber.length() > 20) {
            throw new ValidationException("MachineNumber.value length should be <= 20");
        }

        this.value = trimmedNumber;
    }

    public MachineNumber(final MachineNumber number) {
        this.value = number.getValue();
    }
}
