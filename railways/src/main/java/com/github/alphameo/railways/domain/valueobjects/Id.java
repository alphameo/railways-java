package com.github.alphameo.railways.domain.valueobjects;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class Id {
    private final UUID value;

    private Id(final UUID value) {
        this.value = value;
    }

    public Id() {
        this.value = UUID.randomUUID();
    }

    public static Id fromString(@NonNull final String string) {
        return new Id(UUID.fromString(string));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
