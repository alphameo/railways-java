package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.TrainDto;

public interface TrainService {

    void register(TrainDto train);

    TrainDto findById(Long id);

    TrainDto findByNumber(String number);

    List<TrainDto> listAll();

    void unregister(Long id);

    void insertScheduleEntry(Long trainId, ScheduleEntryDto scheduleEntry, int orderIndex);

    void removeScheduleEntry(Long trainId, int orderIndex);

    void updateSchedule(Long trainId, List<ScheduleEntryDto> schedule);
}
