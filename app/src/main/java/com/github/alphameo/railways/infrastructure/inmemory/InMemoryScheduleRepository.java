package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Schedule;
import com.github.alphameo.railways.domain.repositories.ScheduleRepository;

public class InMemoryScheduleRepository implements ScheduleRepository {

    private final InMemoryStorage<Schedule, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public Schedule add(Schedule schedule) throws IllegalArgumentException {
        if (schedule == null) {
            throw new IllegalArgumentException("Invalid schedule: object is null");
        }
        validate(schedule);
        if (schedule.getId() == null) {
            long id = ++idGenerator;
            schedule.setId(id);
        }

        storage.add(schedule.getId(), schedule);
        return schedule;
    }

    @Override
    public Optional<Schedule> findById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Schedule> findAll() {
        return storage.findAll();
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
