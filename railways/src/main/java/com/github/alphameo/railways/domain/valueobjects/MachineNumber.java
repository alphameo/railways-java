package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.NonNull;
import lombok.Value;

@Value
public class MachineNumber {

    private static int MAX_NUMBER_LENGTH = 20;

    private String value;

    public MachineNumber(@NonNull final String value) {
        var trimmedValue = value.trim();

        if (trimmedValue.isBlank()) {
            throw new ValidationException("MachineNumber.value cannot be blank");
        }

        if (trimmedValue.length() > MAX_NUMBER_LENGTH) {
            throw new ValidationException(
                    String.format("MachineNumber.value length should be <= %s", MAX_NUMBER_LENGTH));
        }

        this.value = trimmedValue;
    }
}
