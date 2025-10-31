package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.NonNull;
import lombok.Value;

@Value
public class LocomotiveModel {

    private static int MAX_MODEL_LENGTH = 50;

    private String value;

    public LocomotiveModel(@NonNull final String value) {
        var trimmedValue = value.trim();

        if (trimmedValue.isBlank()) {
            throw new ValidationException("LocomotiveModel.value cannot be blank");
        }

        if (trimmedValue.length() > MAX_MODEL_LENGTH) {
            throw new ValidationException(
                    String.format("LocomotiveModel.value length must be <= %s", MAX_MODEL_LENGTH));
        }

        this.value = trimmedValue;
    }
}
