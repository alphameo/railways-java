package com.github.alphameo.railways.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineStation {

    private Long lineId;
    private Long stationId;
    private Integer position;
}
