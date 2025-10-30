package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.StationDto;

public interface StationService {

    void register(StationDto station);

    StationDto findById(Long id);

    List<StationDto> listAll();

    void unregister(final Long id);
}
