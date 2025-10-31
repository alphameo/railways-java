package com.github.alphameo.railways.application.dto;

import java.time.LocalDateTime;

import com.github.alphameo.railways.domain.valueobjects.Id;

public record ScheduleEntryDto(Id stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
}
