
package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class StationLocation {

    private static int MAX_NAME_LENGTH = 255;

    private String value;

    public StationLocation(final String location) {
        if (location == null) {
            throw new ValidationException("StationLocation.value cannot be null");
        }

        var trimmedName = location.trim();

        if (trimmedName.isBlank()) {
            throw new ValidationException("StationLocation.value cannot be blank");
        }

        if (trimmedName.length() > MAX_NAME_LENGTH) {
            throw new ValidationException(
                    String.format("StationLocation.value length should be <= %s", MAX_NAME_LENGTH));
        }

        this.value = trimmedName;
    }

    public StationLocation(final StationLocation location) {
        this.value = location.getValue();
    }
}
