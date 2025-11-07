package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.application.dto.StationDto;

public interface LineService {
    void declareLine(LineDto line);

    List<StationDto> listStationsOfLine(String lineId);

    LineDto findLineById(String id);

    List<LineDto> listAllLines();

    void disbandLine(String lineId);
}
