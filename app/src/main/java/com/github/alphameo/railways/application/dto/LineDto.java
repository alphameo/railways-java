package com.github.alphameo.railways.application.dto;

import java.util.List;

public record LineDto(String name, List<Long> stationIds) {
}
