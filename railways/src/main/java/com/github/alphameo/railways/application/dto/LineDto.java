package com.github.alphameo.railways.application.dto;

import java.util.List;

public record LineDto(Long id, String name, List<Long> stationIdOrder) {
}
