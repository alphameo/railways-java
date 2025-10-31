package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.domain.valueobjects.ScheduleEntry;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Train {

    private Id id;
    private MachineNumber number;
    private Id trainCompositionId;
    private List<ScheduleEntry> schedule;

    public Train(final Id id, final MachineNumber number, final Id trainCompositionId, List<ScheduleEntry> schedule) {
        if (id == null) {
            throw new ValidationException("Train.id cannot be null");
        }
        this.id = id;
        this.changeNumber(number);
        this.updateSchedule(schedule);
    }

    public Train(final MachineNumber number, final Id trainCompositionId, List<ScheduleEntry> schedule) {
        this(new Id(), number, trainCompositionId, schedule);
    }

    public List<ScheduleEntry> getSchedule() {
        return List.copyOf(schedule);
    }

    public void changeNumber(final MachineNumber number) {
        if (number == null) {
            throw new ValidationException("Train.number cannot be null");
        }

        this.number = number;
    }

    public void assignTrainComposition(final Id trainCompositionId) {
        if (trainCompositionId == null) {
            throw new ValidationException("Train.trainCompositionId cannot be null");
        }

        this.trainCompositionId = trainCompositionId;
    }

    public void updateSchedule(@NonNull final List<ScheduleEntry> schedule) {
        if (schedule == null) {
            throw new ValidationException("TrainComposition.schedule cannot be null");
        }

        var newSchedule = new ArrayList<ScheduleEntry>();
        var prevEntry = this.schedule.get(0);
        for (ScheduleEntry entry : schedule) {
            if (entry == null) {
                throw new ValidationException("ScheduleEntry cannot be null");
            }
            validateCloseScheduleEntries(prevEntry, entry);
            newSchedule.add(entry);
            prevEntry = entry;
        }

        this.schedule = newSchedule;
    }

    public void insertScheduleEntry(@NonNull final ScheduleEntry scheduleEntry, int orderIndex) {
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
                    String.format("Cannot insert station on orderIndex=%s: %s", orderIndex, e.getMessage()),
                    e);
        }
    }

    public void removeScheduleEntry(final int orderIndex) {
        try {
            this.schedule.remove(orderIndex - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove station on orderIndex=%s: %s", orderIndex, e.getMessage()),
                    e);
        }
    }

    public int scheduleEntriesCount() {
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
            throw new ValidationException("Previous departureTime must be <= past arrivalTime");
        }
    }
}
