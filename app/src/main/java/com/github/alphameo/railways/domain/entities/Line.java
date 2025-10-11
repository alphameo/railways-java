package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;
import lombok.NonNull;

@Data
public class Line {

    private Long id;
    private ObjectName name;
    private List<Long> stationIdOrder;

    public Line(final Long id, final ObjectName name, final List<Long> stationOrder) {
        this.id = id;
        this.setName(name);
        this.setStationIdOrder(stationIdOrder);
    }

    public void setName(final ObjectName name) {
        if (name == null) {
            throw new ValidationException("Line.name cannot be null");
        }

        this.name = name;
    }

    public void setStationIdOrder(List<Long> stationInOrder) {
        if (stationInOrder == null | stationInOrder.isEmpty()) {
            throw new ValidationException("Line.stationsInOrder cannot be null or empty");
        }

        final var newIds = new ArrayList<Long>();

        for (Long id : stationInOrder) {
            if (id == null) {
                throw new ValidationException("stationId cannot be null");
            }

            newIds.add(id);
        }

        this.stationIdOrder = newIds;
    }

    public void addStation(@NonNull final Long id, final int position) {
        try {
            this.stationIdOrder.add(position - 1, id);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot insert station on position=%s: %s", position, e.getMessage()));
        }

    }

    public void removeStationById(@NonNull final Long id) {
        this.stationIdOrder.remove(id);
    }

    public void removeCarriageByPosition(final int position) {
        try {
            this.stationIdOrder.remove(position - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove station on position=%s: %s", position, e.getMessage()));
        }
    }

    public int getCarriageCount() {
        return this.stationIdOrder.size();
    }
}
