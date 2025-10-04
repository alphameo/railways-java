package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Schedule;
import com.github.alphameo.railways.domain.repositories.ScheduleRepository;
import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryScheduleRepository implements ScheduleRepository {

    private final InMemoryStorage<Schedule, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public Schedule create(final Schedule schedule) throws IllegalArgumentException {
        validate(schedule);
        if (schedule.getId() == null) {
            final long id = ++idGenerator;
            schedule.setId(id);
        }

        storage.create(schedule.getId(), schedule);
        return schedule;
    }

    @Override
    public Optional<Schedule> findById(final Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Schedule> findAll() {
        return storage.findAll();
    }

    @Override
    public Schedule update(final Schedule schedule) throws IllegalArgumentException {
        validate(schedule);

        return storage.update(schedule.getId(), schedule);
    }

    @Override
    public void deleteById(final Long id) throws IllegalArgumentException {
        storage.deleteById(id);
    }

    public void validate(final Schedule schedule) throws IllegalArgumentException {
        if (schedule == null) {
            throw new IllegalArgumentException("Schedul cannot be null");
        }
        if (schedule.getTrainId() == null) {
            throw new InMemoryException("Schedule.trainId cannot be null");
        }
        if (schedule.getStationId() == null) {
            throw new InMemoryException("Schedule.stationId cannot be null");
        }
        final var arrT = schedule.getArrivalTime();
        final var depT = schedule.getDepartureTime();
        if (!(arrT == null || depT == null
                || arrT.isBefore(depT)
                || arrT.isEqual(depT))) {
            throw new InMemoryException("Schedule.arrival_time should be <= Schedule.departure_time");
        }
    }
}
