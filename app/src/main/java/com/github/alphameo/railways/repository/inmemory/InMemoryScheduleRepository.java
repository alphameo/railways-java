package com.github.alphameo.railways.repository.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.alphameo.railways.domain.Schedule;
import com.github.alphameo.railways.repository.ScheduleRepository;

public class InMemoryScheduleRepository implements ScheduleRepository {

    private final InMemoryStorage<Schedule, Long> storage = new InMemoryStorage<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Schedule add(Schedule schedule) throws IllegalArgumentException {
        if (schedule == null) {
            throw new IllegalArgumentException("Invalid schedule: object is null");
        }
        validate(schedule);
        if (schedule.getId() == null) {
            long id = idGenerator.incrementAndGet();
            schedule.setId(id);
        }

        storage.add(schedule.getId(), schedule);
        return schedule;
    }

    @Override
    public Optional<Schedule> getById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Schedule> getAll() {
        return storage.getAll();
    }

    @Override
    public boolean update(Schedule schedule) throws IllegalArgumentException {
        if (schedule == null) {
            throw new IllegalArgumentException("Invalid schedule: object is null");
        }
        validate(schedule);

        return storage.update(schedule.getId(), schedule);
    }

    @Override
    public boolean deleteById(Long id) throws IllegalArgumentException {
        return storage.deleteById(id) != null;
    }

    public void validate(Schedule schedule) throws IllegalArgumentException {
        if (schedule.getTrainId() == null) {
            throw new IllegalArgumentException("Invalid schedule: trainId is null");
        }
        if (schedule.getStationId() == null) {
            throw new IllegalArgumentException("Invalid schedule: stationId is null");
        }
        var arrT = schedule.getArrivalTime();
        var depT = schedule.getDepartureTime();
        if (!(arrT == null || depT == null
                || arrT.isBefore(depT)
                || arrT.isEqual(depT))) {
            throw new IllegalArgumentException("Invalid schedule: getArrivalTime <= getDepartureTime");
        }
    }
}
