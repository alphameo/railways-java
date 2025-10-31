package com.github.alphameo.railways.application.dto;

import com.github.alphameo.railways.domain.valueobjects.Id;

public record StationDto(Id id, String name, String location) {
}
