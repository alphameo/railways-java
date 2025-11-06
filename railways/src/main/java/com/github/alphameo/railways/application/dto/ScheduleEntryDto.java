package com.github.alphameo.railways.application.dto;

import java.time.LocalDateTime;

public record ScheduleEntryDto(String stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
}
