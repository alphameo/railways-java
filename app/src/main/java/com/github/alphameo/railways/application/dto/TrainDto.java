package com.github.alphameo.railways.application.dto;

import java.util.List;

public record TrainDto(String number, Long trainCompositionId, List<ScheduleEntryDto> schedule) {
}
