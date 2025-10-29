package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class TrainComposition {

    private Long id;
    private Long locomotiveId;
    private List<Long> carriageIds;

    public TrainComposition(final Long id, Long locomotiveId, @NonNull List<Long> carriageIds) {
        this.id = id;
        this.setLocomotiveId(locomotiveId);
        this.updateCarriages(carriageIds);
    }

    public TrainComposition(final TrainComposition trainComposition) {
        new TrainComposition(
                trainComposition.id,
                trainComposition.locomotiveId,
                trainComposition.carriageIds);
    }

    public List<Long> getCarriageIds() {
        return List.copyOf(this.carriageIds);
    }

    public void setLocomotiveId(Long locomotive) {
        if (locomotive == null) {
            throw new ValidationException("TrainComposition.locomotiveId cannot be null");
        }

        this.locomotiveId = locomotive;
    }

    public void updateCarriages(@NonNull List<Long> carriageIds) {
        if (carriageIds.isEmpty()) {
            throw new ValidationException("TrainComposition.carriageIds should not be empty");
        }

        var newIds = new ArrayList<Long>();
        for (Long id : carriageIds) {
            if (id == null) {
                throw new ValidationException("carriageId in TrainComposition cannot be null");
            }
        }

        this.carriageIds = newIds;
    }

    public void attachCarriage(@NonNull final Long id, final int position) {
        try {
            this.carriageIds.add(position - 1, id);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot insert carriage on position=%s: %s", position, e.getMessage()),
                    e);
        }

    }

    public void unattachCarriageWithId(@NonNull final Long id) {
        this.carriageIds.remove(id);
    }

    public void unattachCarriageOnPosition(final int position) {
        try {
            this.carriageIds.remove(position - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove carriage on position=%s: %s", position, e.getMessage()),
                    e);
        }
    }

    public int carriageCount() {
        return this.carriageIds.size();
    }
}
