package com.github.alphameo.railways.application.dto;

import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.Id;

public record TrainDto(Id id, String number, Id trainCompositionId, List<ScheduleEntryDto> schedule) {
}
