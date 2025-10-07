package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.entities.LineStation;
import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.LineStationRepository;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.exceptions.application.services.ServiceException;

public class LineService {

    // private LineRepository lineRepository;
    private StationRepository stationRepository;
    private LineStationRepository lineStationRepository;

    public Station registerStation(final String name, final String location) {
        try {
            final var station = new Station(null, name, location);
            stationRepository.create(station);
            return station;
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void unregisterStation(final Long stationId) {
        try {
            stationRepository.deleteById(stationId);
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Line declareLine(final String name, final List<Long> stations) {
        if (stations.isEmpty() || stations == null) {
            throw new ServiceException("Line should contain minimum one station, but given 0");
        }
        try {
            final var line = new Line(null, name);
            for (int i = 0; i < stations.size(); i++) {
                final var id = stations.get(i);
                final var station = stationRepository.findById(id);
                final var lineStation = new LineStation(line.getId(), station.get().getId(), i + 1);
                lineStationRepository.create(lineStation);
            }
            return line;
        } catch (final Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    // TODO: make after joins
    // public List<Station> listLineStations(final Long lineId) {
    //     try {
    //         final var stationIds = lineStationRepository.findStationIdsByLineId(lineId);
    //         final var sortedStationIds = new Long[stationIds.size()];
    //         for (final var stationId : stationIds) {
    //             final Station station = 
    //         }
    //     } catch (Exception e) {
    //         throw new ServiceException(e.getMessage());
    //     }
    // }

    public void disbandLine(Long lineId) {
        try {
            final var stationIds = lineStationRepository.findStationIdsByLineId(lineId);
            for (final var stationId : stationIds) {
                lineStationRepository.deleteById(new LineStation.LineStationId(lineId, stationId));
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
