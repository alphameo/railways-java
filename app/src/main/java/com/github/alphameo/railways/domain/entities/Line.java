package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Line {

    private Long id;
    private ObjectName name;
    private List<Long> stationIdOrder;

    public Line(final Long id, final ObjectName name, final List<Long> stationIdOrder) {
        this.id = id;
        this.rename(name);
        this.updateStationIds(stationIdOrder);
    }

    public List<Long> getStationIdOrder() {
        return List.copyOf(this.stationIdOrder);
    }

    public void rename(final ObjectName name) {
        if (name == null) {
            throw new ValidationException("Line.name cannot be null");
        }

        this.name = name;
    }

    public void updateStationIds(@NonNull final List<Long> stationIds) {
        if (stationIds.isEmpty()) {
            throw new ValidationException("Line.stationIds cannot be empty");
        }

        final var newIds = new ArrayList<Long>();

        for (Long id : stationIds) {
            if (id == null) {
                throw new ValidationException("stationId cannot be null");
            }

            newIds.add(id);
        }

        this.stationIdOrder = newIds;
    }

    public void registerStation(@NonNull final Long stationId, final int position) {
        try {
            this.stationIdOrder.add(position - 1, stationId);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot insert station on position=%s: %s", position, e.getMessage()));
        }

    }

    public void unregisterStationById(@NonNull final Long id) {
        this.stationIdOrder.remove(id);
    }

    public void unregisterStationOnPosition(final int position) {
        try {
            this.stationIdOrder.remove(position - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove station on position=%s: %s", position, e.getMessage()));
        }
    }

    public int stationCount() {
        return this.stationIdOrder.size();
    }
}
