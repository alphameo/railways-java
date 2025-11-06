package com.github.alphameo.railways.application.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.alphameo.railways.domain.valueobjects.Id;

import lombok.NonNull;

public class IdMapper {
    public static List<String> toStringList(@NonNull final List<Id> ids) {
        final var stringIds = new ArrayList<String>();
        for (final var id : ids) {
            stringIds.add(id.toString());
        }
        return stringIds;
    }

    public static List<Id> toIdList(@NonNull final List<String> stringIds) {
        final var ids = new ArrayList<Id>();
        for (final var id : stringIds) {
            ids.add(Id.fromString(id));
        }
        return ids;
    }
}
