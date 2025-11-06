package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.domain.valueobjects.Id;

public interface TrainService {

    void register(TrainDto train);

    TrainDto findById(Id id);

    TrainDto findByNumber(String number);

    List<TrainDto> listAll();

    void unregister(String id);

    void insertScheduleEntry(String trainId, ScheduleEntryDto scheduleEntry, int orderIndex);

    void removeScheduleEntry(String trainId, int orderIndex);

    void updateSchedule(String trainId, List<ScheduleEntryDto> schedule);
}
