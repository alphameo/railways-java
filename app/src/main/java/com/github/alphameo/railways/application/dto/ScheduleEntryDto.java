package com.github.alphameo.railways.application.dto;

import java.time.LocalDateTime;

public record ScheduleEntryDto(Long stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
}
