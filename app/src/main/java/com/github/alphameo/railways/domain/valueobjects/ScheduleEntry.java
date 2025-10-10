package com.github.alphameo.railways.domain.valueobjects;

import java.time.LocalDateTime;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ScheduleEntry {

    private Long stationId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public ScheduleEntry(Long stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        if (stationId == null) {
            throw new ValidationException("Schedule.stationId cannot be null");
        }
        validateTime(this.arrivalTime, departureTime);

        this.stationId = stationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

    }

    private static void validateTime(final LocalDateTime arrT, final LocalDateTime depT) {
        if (!(arrT == null || depT == null
                || arrT.isBefore(depT)
                || arrT.isEqual(depT))) {
            throw new ValidationException("Schedule.arrival_time should be <= Schedule.departure_time");
        }
    }
}
