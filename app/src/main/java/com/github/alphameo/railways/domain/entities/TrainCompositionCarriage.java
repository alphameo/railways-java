package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class TrainCompositionCarriage {

    @Setter(AccessLevel.NONE)
    private TrainCompositionCarriageId id = new TrainCompositionCarriageId();
    private Integer position;

    public TrainCompositionCarriage(Long trainCompositionId, Long carriageId, Integer position) {
        this.id.trainCompositionId = trainCompositionId;
        this.id.carriageId = carriageId;
        this.position = position;
    }

    public Long getTrainCompositionId() {
        return this.id.trainCompositionId;
    }

    public Long getCarriageId() {
        return this.id.carriageId;
    }

    public void setTrainCompositionId(Long trainCompositionId) {
        if (trainCompositionId == null) {
            throw new ValidationException("TrainCompositionCarriage.trainCompositionId cannot be null");
        }

        this.id.trainCompositionId = trainCompositionId;
    }

    public void setCarriageId(Long carriageId) {
        if (carriageId == null) {
            throw new ValidationException("TrainCompositionCarriage.carriageId cannot be null");
        }

        this.id.carriageId = carriageId;
    }

    public void setPosition(final Integer position) {
        if (position == null) {
            throw new ValidationException("TrainCompositionCarriage.position cannot be null");
        }
        if (position < 0) {
            throw new ValidationException("TrainCompositionCarriage.position must be >= 0");
        }

        this.position = position;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrainCompositionCarriageId {

        private Long trainCompositionId;
        private Long carriageId;
    }
}
