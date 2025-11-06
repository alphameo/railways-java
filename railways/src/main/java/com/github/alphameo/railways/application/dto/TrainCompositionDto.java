package com.github.alphameo.railways.application.dto;

import java.util.List;

public record TrainCompositionDto(String id, String locomotiveId, List<String> carriageIds) {
}
