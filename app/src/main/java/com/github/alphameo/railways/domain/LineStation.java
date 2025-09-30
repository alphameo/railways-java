package com.github.alphameo.railways.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class LineStation {

    @Setter(AccessLevel.NONE)
    private final LineStationId id = new LineStationId();
    private Integer position;

    public LineStation(Long lineId, Long stationId, Integer position) {
        this.id.lineId = lineId;
        this.id.stationId = stationId;
        this.position = position;
    }

    public Long getLineId() {
        return this.id.lineId;
    }

    public Long getStationId() {
        return this.id.stationId;
    }

    public void setLineId(Long id) {
        this.id.lineId = id;
    }

    public void setStationId(Long id) {
        this.id.stationId = id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LineStationId {

        private Long lineId;
        private Long stationId;
    }
}
