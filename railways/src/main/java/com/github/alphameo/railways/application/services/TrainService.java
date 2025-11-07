package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.TrainDto;

public interface TrainService {

    void registerTrain(TrainDto train);

    TrainDto findTrainById(String id);

    TrainDto findTrainByNumber(String number);

    List<TrainDto> listAll();

    void unregisterTrain(String id);

    void insertScheduleEntry(String trainId, ScheduleEntryDto scheduleEntry, int orderIndex);

    void removeScheduleEntry(String trainId, int orderIndex);

    void updateSchedule(String trainId, List<ScheduleEntryDto> schedule);
}
