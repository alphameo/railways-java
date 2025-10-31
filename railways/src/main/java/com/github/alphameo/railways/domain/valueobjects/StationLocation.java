
package com.github.alphameo.railways.domain.valueobjects;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.NonNull;
import lombok.Value;

@Value
public class StationLocation {

    private static int MAX_LOCATION_NAME_LENGTH = 255;

    private String value;

    public StationLocation(@NonNull final String value) {
        var trimmedValue = value.trim();

        if (trimmedValue.isBlank()) {
            throw new ValidationException("stationLocation: value cannot be blank");
        }

        if (trimmedValue.length() > MAX_LOCATION_NAME_LENGTH) {
            throw new ValidationException(
                    String.format("stationLocation: value length must be <= %s", MAX_LOCATION_NAME_LENGTH));
        }

        this.value = trimmedValue;
    }
}
