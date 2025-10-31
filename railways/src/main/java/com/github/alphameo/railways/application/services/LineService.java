package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LineDto;
import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.domain.valueobjects.Id;

public interface LineService {
    void declareLine(LineDto line);

    List<StationDto> listLineStations(Id lineId);

    LineDto findById(Id id);

    List<LineDto> listAll();

    void disbandLine(Id lineId);
}
