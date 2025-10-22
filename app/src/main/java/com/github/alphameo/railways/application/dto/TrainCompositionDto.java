package com.github.alphameo.railways.application.dto;

import java.util.List;

public record TrainCompositionDto(Long locomotiveId, List<Long> carriageIds) {
}
