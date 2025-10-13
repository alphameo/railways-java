package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MachineNumber {

    private static int MAX_NUMBER_LENGTH = 20;

    private String value;

    public MachineNumber(final String number) {
        if (number == null) {
            throw new ValidationException("MachineNumber.value cannot be null");
        }

        var trimmedNumber = number.trim();

        if (trimmedNumber.isBlank()) {
            throw new ValidationException("MachineNumber.value cannot be blank");
        }

        if (trimmedNumber.length() > MAX_NUMBER_LENGTH) {
            throw new ValidationException(
                    String.format("MachineNumber.value length should be <= %s", MAX_NUMBER_LENGTH));
        }

        this.value = trimmedNumber;
    }

    public MachineNumber(final MachineNumber number) {
        this.value = number.getValue();
    }
}
