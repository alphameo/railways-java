package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ObjectName {

    private String value;

    public ObjectName(final String name) {
        if (name == null) {
            throw new ValidationException("ObjectName.value cannot be null");
        }

        var trimmedName = name.trim();

        if (trimmedName.isBlank()) {
            throw new ValidationException("ObjectName.value cannot be blank");
        }

        if (trimmedName.length() > 100) {
            throw new ValidationException("ObjectName.value length should be <= 100");
        }

        this.value = trimmedName;
    }

    public ObjectName(final ObjectName name) {
        this.value = name.getValue();
    }
}
