package com.github.alphameo.railways.application.dto;

import java.util.List;

public record LineDto(String id, String name, List<String> stationIdOrder) {
}
