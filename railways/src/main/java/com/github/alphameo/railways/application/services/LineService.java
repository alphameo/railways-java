package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.application.dto.StationDto;

public interface LineService {
    void declareLine(LineDto line);

    List<StationDto> listLineStations(Long lineId);

    LineDto findById(Long id);

    List<LineDto> listAll();

    void disbandLine(Long lineId);
}
