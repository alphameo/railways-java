package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class TrainComposition {

    private Long id;
    private Long trainId;
    private Long locomotiveId;

    public TrainComposition(final Long id, final Long trainId, final Long locomotiveId) {
        this.id = id;
        this.setTrainId(trainId);
        this.locomotiveId = locomotiveId;
    }

    public void setTrainId(final Long trainId) {
        if (trainId == null) {
            throw new ValidationException("TrainComposition.trainId cannot be null");
        }

        this.trainId = trainId;
    }
}
