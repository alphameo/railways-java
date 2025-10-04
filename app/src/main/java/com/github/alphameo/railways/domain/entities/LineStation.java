package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class LineStation {

    @Setter(AccessLevel.NONE)
    private final LineStationId id = new LineStationId();
    private Integer position;

    public LineStation(final Long lineId, final Long stationId, final Integer position) {
        this.setLineId(lineId);
        this.setStationId(stationId);
        this.setPosition(position);
    }

    public Long getLineId() {
        return this.id.lineId;
    }

    public Long getStationId() {
        return this.id.stationId;
    }

    public void setLineId(final Long lineId) {
        if (lineId == null) {
            throw new ValidationException("LineStation.lineId cannot be null");
        }

        this.id.lineId = lineId;
    }

    public void setStationId(final Long stationId) {
        if (stationId == null) {
            throw new ValidationException("LineStation.stationId cannot be null");
        }

        this.id.stationId = stationId;
    }

    public void setPosition(final Integer position) {
        if (position == null) {
            throw new ValidationException("LineStation.position cannot be null");
        }
        if (position < 0) {
            throw new ValidationException("LineStation.position must be >= 0");
        }

        this.position = position;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LineStationId {

        private Long lineId;
        private Long stationId;
    }
}
