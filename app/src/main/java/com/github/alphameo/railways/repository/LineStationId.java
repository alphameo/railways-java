package com.github.alphameo.railways.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineStationId {

    private Long lineId;
    private Long StationId;
}
