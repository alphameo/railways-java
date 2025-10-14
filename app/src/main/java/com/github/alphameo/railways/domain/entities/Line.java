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

    private List<Long> stationIds;

    public Line(final Long id, final ObjectName name, final List<Long> stationOrder) {
        this.id = id;
        this.rename(name);
        this.updateStationIds(stationIds);
    }

    public List<Long> getStationIds() {
        return List.copyOf(this.stationIds);
    }

    public void rename(final ObjectName name) {
        if (name == null) {
            throw new ValidationException("Line.name cannot be null");
        }

        this.name = name;
    }

    public void updateStationIds(List<Long> stationIds) {
        if (stationIds == null | stationIds.isEmpty()) {
            throw new ValidationException("Line.stationsIds cannot be null or empty");
        }

        final var newIds = new ArrayList<Long>();

        for (Long id : stationIds) {
            if (id == null) {
                throw new ValidationException("stationId cannot be null");
            }

            newIds.add(id);
        }

        this.stationIds = newIds;
    }

    public void registerStation(@NonNull final Long stationId, final int position) {
        try {
            this.stationIds.add(position - 1, stationId);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot insert station on position=%s: %s", position, e.getMessage()));
        }

    }

    public void unregisterStationById(@NonNull final Long id) {
        this.stationIds.remove(id);
    }

    public void unregisterStationOnPosition(final int position) {
        try {
            this.stationIds.remove(position - 1);
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Cannot remove station on position=%s: %s", position, e.getMessage()));
        }
    }

    public int stationCount() {
        return this.stationIds.size();
    }
}
