package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;
import lombok.NonNull;

@Data
public class TrainComposition {

    private Long id;
    private Long locomotiveId;
    private ArrayList<Long> carriageIds;

    public TrainComposition(final Long id, Long locomotiveId, @NonNull List<Long> carriageIds) {
        this.id = id;
        this.setLocomotiveId(locomotiveId);
        this.setCarriages(carriageIds);
    }

    public TrainComposition(final TrainComposition trainComposition) {
        new TrainComposition(
                trainComposition.id,
                trainComposition.locomotiveId,
                trainComposition.carriageIds);
    }

    public void setLocomotiveId(Long locomotive) {
        if (locomotive == null) {
            throw new ValidationException("TrainComposition.locomotiveId cannot be null");
        }

        this.locomotiveId = locomotive;
    }

    public void setCarriages(@NonNull List<Long> carriageIds) {
        if (carriageIds == null | carriageIds.isEmpty()) {
            throw new ValidationException("TrainComposition.carriageIds should not be empty or null");
        }

        var newIds = new ArrayList<Long>();
        for (Long id : carriageIds) {
            if (id == null) {
                throw new ValidationException("carriageId in TrainComposition cannot be null");
            }
        }

        this.carriageIds = newIds;
    }

    public void addCarriage(@NonNull final Long id, final int position) {
        try {
            this.carriageIds.add(position, id);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot insert carriage on position=%s: %s", position, e.getMessage()));
        }

    }

    public void removeCarriageById(@NonNull final Long id) {
        this.carriageIds.remove(id);
    }

    public void removeCarriageByPosition(final int position) {
        try {
            this.carriageIds.remove(position);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove carriage on position=%s: %s", position, e.getMessage()));
        }
    }

    public int getCarriageCount() {
        return this.carriageIds.size();
    }
}
