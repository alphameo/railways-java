package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.ScheduleEntry;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;
import lombok.NonNull;

@Data
public class Train {

    private Long id;
    private String number;
    private Long trainCompositionId;
    private List<ScheduleEntry> schedule;

    public Train(final Long id, final String number, final Long trainCompositionId, List<ScheduleEntry> schdeule) {
        this.id = id;
        this.setNumber(number);
    }

    public Train(final Train train) {
        new Train(
                train.id,
                train.number,
                train.trainCompositionId,
                train.schedule);
    }

    public void setNumber(final String number) {
        if (number == null) {
            throw new ValidationException("Train.number cannot be null");
        }

        this.number = number;
    }

    public void setTrainCompositionId(Long trainCompositionId) {
        if (trainCompositionId == null) {
            throw new ValidationException("Train.trainCompositionId cannot be null");
        }

        this.trainCompositionId = trainCompositionId;
    }

    public void setSchedule(@NonNull final List<ScheduleEntry> schedule) {
        if (schedule == null | schedule.isEmpty()) {
            throw new ValidationException("TrainComposition.carriageIds should not be empty or null");
        }

        var newSchedule = new ArrayList<ScheduleEntry>();
        var prevEntry = this.schedule.get(0);
        for (ScheduleEntry entry : schedule) {
            if (entry == null) {
                throw new ValidationException("carriageId in TrainComposition cannot be null");
            }
            validateCloseScheduleEntries(prevEntry, entry);
            newSchedule.add(entry);
            prevEntry = entry;
        }

        this.schedule = newSchedule;
    }

    public void addScheduleEntry(@NonNull final ScheduleEntry scheduleEntry, int orderIndex) {
        var index = orderIndex - 1;
        if (index > 0) {
            var prevEntry = this.schedule.get(index - 1);
            validateCloseScheduleEntries(prevEntry, scheduleEntry);
        }
        if (index < this.schedule.size() - 1) {
            var pastEntry = this.schedule.get(index + 1);
            validateCloseScheduleEntries(scheduleEntry, pastEntry);
        }
        try {
            this.schedule.add(index, scheduleEntry);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot insert station on orderIndex=%s: %s", orderIndex, e.getMessage()));
        }
    }

    public void removeScheduleEntryByOrederIndex(final int orderIndex) {
        try {
            this.schedule.remove(orderIndex - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove station on orderIndex=%s: %s", orderIndex, e.getMessage()));
        }
    }

    public int getScheduleEntriesCount() {
        return this.schedule.size();
    }

    private void validateCloseScheduleEntries(final ScheduleEntry prev, final ScheduleEntry past) {
        if (prev.getDepartureTime() == null) {
            if (past.getArrivalTime() != null) {
                throw new ValidationException(
                        "arrivalTime cannot be specified if last departureTime is not");
            }
        }
        if (past.getArrivalTime() == null) {
            return;
        }
        if (past.getArrivalTime().isBefore(prev.getDepartureTime())) {
            throw new ValidationException("Previous departureTime should be <= past arrivalTime");
        }
    }
}
