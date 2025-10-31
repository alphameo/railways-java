package com.github.alphameo.railways.application.dto;

import com.github.alphameo.railways.domain.valueobjects.Id;

public record CarriageDto(Id id, String number, String contentType, long capacity) {
}
