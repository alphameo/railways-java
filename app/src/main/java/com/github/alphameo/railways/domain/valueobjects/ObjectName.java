package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Value;

@Value
public class ObjectName {

    private static int MAX_NUMBER_LENGTH = 100;

    private String value;

    public ObjectName(final String name) {
        if (name == null) {
            throw new ValidationException("ObjectName.value cannot be null");
        }

        var trimmedName = name.trim();

        if (trimmedName.isBlank()) {
            throw new ValidationException("ObjectName.value cannot be blank");
        }

        if (trimmedName.length() > MAX_NUMBER_LENGTH) {
            throw new ValidationException(String.format("ObjectName.value length should be <= %s", MAX_NUMBER_LENGTH));
        }

        this.value = trimmedName;
    }

    public ObjectName(final ObjectName name) {
        this.value = name.getValue();
    }
}
