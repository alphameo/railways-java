package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;

public class InMemoryLineRepository implements LineRepository {

    private final InMemoryStorage<Line, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public Line create(final Line line) {
        validate(line);

        if (line.getId() == null) {
            final long id = ++idGenerator;
            line.setId(id);
        }
        return storage.create(line.getId(), line);
    }

    @Override
    public Optional<Line> findById(final Long id) {
        return storage.getById(id);
    }

    @Override
    public List<Line> findAll() {
        return storage.findAll();
    }

    @Override
    public Line update(final Line line) {
        validate(line);

        return storage.update(line.getId(), line);
    }

    @Override
    public void deleteById(final Long id) {
        storage.deleteById(id);
    }

    public void validate(final Line line) {
        if (line == null) {
            throw new IllegalArgumentException("Line cannot be null");
        }
        if (line.getName() == null) {
            throw new InMemoryNotNullConstraintException("Line.name");
        }
    }
}
