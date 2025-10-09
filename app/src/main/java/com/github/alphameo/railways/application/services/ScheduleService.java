package com.github.alphameo.railways.application.services;

import com.github.alphameo.railways.domain.repositories.ScheduleRepository;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.repositories.TrainRepository;

public class ScheduleService {

    private TrainRepository trainRepository;
    private ScheduleRepository scheduleRepository;
    private StationRepository stationRepository;
}
