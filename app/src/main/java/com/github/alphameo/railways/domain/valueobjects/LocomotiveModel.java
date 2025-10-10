package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LocomotiveModel {

    private String value;

    public LocomotiveModel(final String number) {
        if (number == null) {
            throw new ValidationException("LocomotiveModel.value cannot be null");
        }

        var trimmedNumber = number.trim();

        if (trimmedNumber.isBlank()) {
            throw new ValidationException("LocomotiveModel.value cannot be blank");
        }

        if (trimmedNumber.length() > 50) {
            throw new ValidationException("LocomotiveModel.value length should be <= 50");
        }

        this.value = trimmedNumber;
    }

    public LocomotiveModel(final LocomotiveModel number) {
        this.value = number.getValue();
    }
}
