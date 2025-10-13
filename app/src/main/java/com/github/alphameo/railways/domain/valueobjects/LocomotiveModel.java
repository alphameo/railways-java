package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LocomotiveModel {

    private static int MAX_NUMBER_LENGTH = 50;

    private String value;

    public LocomotiveModel(final String number) {
        if (number == null) {
            throw new ValidationException("LocomotiveModel.value cannot be null");
        }

        var trimmedNumber = number.trim();

        if (trimmedNumber.isBlank()) {
            throw new ValidationException("LocomotiveModel.value cannot be blank");
        }

        if (trimmedNumber.length() > MAX_NUMBER_LENGTH) {
            throw new ValidationException(
                    String.format("LocomotiveModel.value length should be <= %s", MAX_NUMBER_LENGTH));
        }

        this.value = trimmedNumber;
    }

    public LocomotiveModel(final LocomotiveModel number) {
        this.value = number.getValue();
    }
}
