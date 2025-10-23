package com.github.alphameo.railways.application.dto;

import java.util.List;

public record TrainDto(Long id, String number, Long trainCompositionId, List<ScheduleEntryDto> schedule) {
}
