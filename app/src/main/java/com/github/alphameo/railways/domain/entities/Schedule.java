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
        if (arrivalTime != null && this.departureTime != null && arrivalTime.isAfter(this.departureTime)) {
            throw new ValidationException("Schedule.arrival_time should be <= schedule.departure_time");
        }

        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(final LocalDateTime departureTime) {
        if (this.arrivalTime != null && departureTime != null && this.arrivalTime.isAfter(departureTime)) {
            throw new ValidationException("Schedule.arrival_time should be <= schedule.departure_time");
        }

        this.departureTime = departureTime;
    }
}
