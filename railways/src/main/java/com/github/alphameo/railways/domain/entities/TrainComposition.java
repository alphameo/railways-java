package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class TrainComposition {

    private Id id;
    private Id locomotiveId;
    private List<Id> carriageIds;

    public TrainComposition(final Id id, final Id locomotiveId, List<Id> carriageIds) {
        if (id == null) {
            throw new ValidationException("trainComposition: id cannot be null");
        }
        this.id = id;
        this.setLocomotive(locomotiveId);
        this.updateCarriages(carriageIds);
    }

    public TrainComposition(final Id locomotiveId, final List<Id> carriageIds) {
        this(new Id(), locomotiveId, carriageIds);
    }

    public TrainComposition(final TrainComposition trainComposition) {
        new TrainComposition(
                trainComposition.id,
                trainComposition.locomotiveId,
                trainComposition.carriageIds);
    }

    public List<Id> getCarriageIds() {
        return List.copyOf(this.carriageIds);
    }

    public void setLocomotive(final Id locomotiveId) {
        if (locomotiveId == null) {
            throw new ValidationException("trainComposition: locomotiveId cannot be null");
        }

        this.locomotiveId = locomotiveId;
    }

    public void updateCarriages(@NonNull final List<Id> carriageIds) {
        // NOTE: business logic, but hard to show in demo
        // if (carriageIds.isEmpty()) {
        //     throw new ValidationException("trainComposition: carriageIds cannot not be empty");
        // }

        final var newIds = new ArrayList<Id>();
        for (final Id id : carriageIds) {
            if (id == null) {
                throw new ValidationException("trainComposition: carriageId cannot be null");
            }
        }

        this.carriageIds = newIds;
    }

    public void attachCarriage(@NonNull final Id id, final int position) {
        try {
            this.carriageIds.add(position - 1, id);
        } catch (final Exception e) {
            throw new ValidationException(
                    String.format("trainComposition: cannot attach carriage to position=%s: %s", position,
                            e.getMessage()),
                    e);
        }

    }

    public void unattachCarriageWithId(@NonNull final Id id) {
        this.carriageIds.remove(id);
    }

    public void unattachCarriageOnPosition(final int position) {
        try {
            this.carriageIds.remove(position - 1);
        } catch (final Exception e) {
            throw new ValidationException(
                    String.format("trainComposition: cannot unattach carriage from position=%s: %s", position,
                            e.getMessage()),
                    e);
        }
    }

    public int carriageCount() {
        return this.carriageIds.size();
    }
}
