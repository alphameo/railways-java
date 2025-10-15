package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityAlreadyExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;

import lombok.NonNull;

public class InMemoryLineRepository implements LineRepository {

    private final Map<Long, Line> storage = new HashMap<>();
    private Long idGenerator = 0L;

    @Override
    public void create(@NonNull final Line line) {
        validate(line);

        Long id = line.getId();
        if (line.getId() == null) {
            id = ++idGenerator;
        } else {
            if (storage.containsKey(id)) {
                throw new InMemoryEntityAlreadyExistsException("Line", id);
            }
        }

        final var newLine = createLine(id, line);
        storage.put(id, newLine);
    }

    @Override
    public Optional<Line> findById(@NonNull final Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Line> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(@NonNull final Line line) {
        validate(line);
        final var id = line.getId();
        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(line.getClass().toString(), id);
        }

        final var newLine = createLine(id, line);
        storage.put(id, newLine);
    }

    @Override
    public void deleteById(@NonNull final Long id) {
        storage.remove(id);
    }

    private static void validate(final Line line) {
        if (line.getName() == null) {
            throw new InMemoryNotNullConstraintException("Line.name");
        }
    }

    private static Line createLine(final long id, Line l) {
        return new Line(
                id,
                l.getName(),
                l.getStationIds());
    }
}
