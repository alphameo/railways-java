package com.github.alphameo.railways.domain.repositories;

import java.util.List;

import com.github.alphameo.railways.domain.entities.LineStation;
import com.github.alphameo.railways.domain.entities.LineStation.LineStationId;

public interface LineStationRepository extends Repository<LineStation, LineStationId> {

    List<Long> findStationIdsByLineId(Long lineId);

    List<Long> findLineIdsByStationId(Long stationId);
}
