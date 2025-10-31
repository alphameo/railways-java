package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.NonNull;
import lombok.Value;

@Value
public class ObjectName {

    private static int MAX_OBJECT_NAME_LENGTH = 100;

    private String value;

    public ObjectName(@NonNull final String value) {
        var trimmedValue = value.trim();

        if (trimmedValue.isBlank()) {
            throw new ValidationException("objectName: value cannot be blank");
        }

        if (trimmedValue.length() > MAX_OBJECT_NAME_LENGTH) {
            throw new ValidationException(String.format("objectName: value length must be <= %s", MAX_OBJECT_NAME_LENGTH));
        }

        this.value = trimmedValue;
    }
}
