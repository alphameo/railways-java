package com.github.alphameo.railways.application.dto;

import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.Id;

public record TrainCompositionDto(Id id, Id locomotiveId, List<Id> carriageIds) {
}
