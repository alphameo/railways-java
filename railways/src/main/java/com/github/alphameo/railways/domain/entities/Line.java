package com.github.alphameo.railways.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.Id;
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

    private Id id;
    private ObjectName name;
    private List<Id> stationIdOrder;

    public Line(final Id id, final ObjectName name, final List<Id> stationIdOrder) {
        if (id == null) {
            throw new ValidationException("line: id cannot be null");
        }
        this.id = id;
        this.rename(name);
        this.updateStationIdOrder(stationIdOrder);
    }

    public Line(final ObjectName name, final List<Id> stationIdOrder) {
        this(new Id(), name, stationIdOrder);
    }

    public List<Id> getStationIdOrder() {
        return List.copyOf(this.stationIdOrder);
    }

    public void rename(final ObjectName name) {
        if (name == null) {
            throw new ValidationException("line: name cannot be null");
        }

        this.name = name;
    }

    public void updateStationIdOrder(@NonNull final List<Id> stationIdOrder) {
        if (stationIdOrder.isEmpty()) {
            throw new ValidationException("line: stationIdOrder cannot be empty");
        }

        final var newIds = new ArrayList<Id>();

        for (Id id : stationIdOrder) {
            if (id == null) {
                throw new ValidationException("line: stationId in stationIdOrder cannot be null");
            }

            newIds.add(id);
        }

        this.stationIdOrder = newIds;
    }

    public void registerStation(@NonNull final Id stationId, final int position) {
        try {
            this.stationIdOrder.add(position - 1, stationId);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("line: cannot insert station on position=%s: %s", position, e.getMessage()),
                    e);
        }

    }

    public void unregisterStationById(@NonNull final Id id) {
        this.stationIdOrder.remove(id);
    }

    public void unregisterStationOnPosition(final int position) {
        try {
            this.stationIdOrder.remove(position - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("line: cannot remove station on position=%s: %s", position, e.getMessage()),
                    e);
        }
    }

    public int stationCount() {
        return this.stationIdOrder.size();
    }
}
