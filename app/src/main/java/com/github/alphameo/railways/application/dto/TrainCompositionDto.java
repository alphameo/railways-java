package com.github.alphameo.railways.application.dto;

import java.util.List;

public record TrainCompositionDto(Long id, Long locomotiveId, List<Long> carriageIds) {
}
