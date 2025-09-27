package com.github.alphameo.railways.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Long train_id;
    private Long station_id;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}
