package com.github.alphameo.railways.domain.entities;

import java.time.LocalDateTime;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Schedule {

    private Long id;
    private Long trainId;
    private Long stationId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public Schedule(Long id, Long trainId, Long stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.id = id;
        this.setTrainId(trainId);
        this.setStationId(stationId);
        this.setArrivalTime(arrivalTime);
        this.setDepartureTime(departureTime);
    }

    public void setTrainId(final Long trainId) {
        if (trainId == null) {
            throw new ValidationException("Schedule.trainId cannot be null");
        }

        this.trainId = trainId;
    }

    public void setStationId(final Long stationId) {
        if (stationId == null) {
            throw new ValidationException("Schedule.stationId cannot be null");
        }

        this.stationId = stationId;
    }

    public void setArrivalTime(final LocalDateTime arrivalTime) {
        validateTime(this.arrivalTime, departureTime);

        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(final LocalDateTime departureTime) {
        validateTime(this.arrivalTime, departureTime);

        this.departureTime = departureTime;
    }

    private static void validateTime(final LocalDateTime arrT, final LocalDateTime depT) {
        if (!(arrT == null || depT == null
                || arrT.isBefore(depT)
                || arrT.isEqual(depT))) {
            throw new ValidationException("Schedule.arrival_time should be <= Schedule.departure_time");
        }
    }
}
