package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.domain.valueobjects.Id;

public interface StationService {

    void register(StationDto station);

    StationDto findById(Id id);

    List<StationDto> listAll();

    void unregister(final Id id);
}
