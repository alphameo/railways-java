package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.StationDto;

public interface StationService {

    void registerStation(StationDto station);

    StationDto findStationById(String id);

    List<StationDto> listAllStations();

    void unregisterStation(String id);
}
