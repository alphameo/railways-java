package com.github.alphameo.railways.application.dto;

import java.util.List;

public record TrainDto(String id, String number, String trainCompositionId, List<ScheduleEntryDto> schedule) {
}
