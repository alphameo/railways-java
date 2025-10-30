package com.github.alphameo.railways.domain.valueobjects;

public enum CarriageContentType {
    PASSENGER,
    CARGO;

    public static CarriageContentType create(final String type) {
        if (type == null) {
            return null;
        }
        final String processed = type.trim().toLowerCase();
        return switch (processed) {
            case "passenger" -> PASSENGER;
            case "cargo" -> CARGO;
            case "none", "null" -> null;
            default -> throw new IllegalArgumentException(String.format("Unknown carriage content type: %s", type));
        };
    }
}
