package com.github.alphameo.railways.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
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

    public void setTrainCompositionId(Long id) {
        this.id.trainCompositionId = id;
    }

    public void setCarriageId(Long id) {
        this.id.carriageId = id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TrainCompositionCarriageId {

        private Long trainCompositionId;
        private Long carriageId;
    }
}
