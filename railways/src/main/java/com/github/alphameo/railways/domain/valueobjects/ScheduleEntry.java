package com.github.alphameo.railways.domain.valueobjects;

import java.time.LocalDateTime;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Value;

@Value
public class ScheduleEntry {

    private Id stationId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public ScheduleEntry(Id stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        if (stationId == null) {
            throw new ValidationException("scheduleEntry: stationId cannot be null");
        }
        validateTime(arrivalTime, departureTime);

        this.stationId = stationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

    }

    private static void validateTime(final LocalDateTime arrT, final LocalDateTime depT) {
        if (depT == null) {
            return;
        }
        if (arrT == null) {
            throw new ValidationException(
                    "scheduleEntry: arrivalTime must be set if departureTime already been set");
        }
        if (arrT.isAfter(depT)) {
            throw new ValidationException("scheduleEntry: arrivalTime must be <= departureTime");
        }
    }
}
