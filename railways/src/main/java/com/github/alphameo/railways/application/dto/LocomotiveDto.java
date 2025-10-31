package com.github.alphameo.railways.application.dto;

import com.github.alphameo.railways.domain.valueobjects.Id;

public record LocomotiveDto(Id id, String number, String model) {
}
