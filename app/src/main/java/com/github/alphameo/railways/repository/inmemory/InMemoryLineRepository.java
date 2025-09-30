package com.github.alphameo.railways.repository.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.alphameo.railways.domain.Line;
import com.github.alphameo.railways.repository.Repository;

public class InMemoryLineRepository implements Repository<Line, Long> {

    private final InMemoryStorage<Line, Long> storage = new InMemoryStorage<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Line add(Line line) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException("Invalid line: object is null");
        }
        validate(line);
        if (line.getId() == null) {
            long id = idGenerator.incrementAndGet();
            line.setId(id);
        }

        storage.add(line.getId(), line);
        return line;
    }

    @Override
    public Optional<Line> getById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Line> getAll() {
        return storage.getAll();
    }

    @Override
    public boolean update(Line line) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException("Invalid line: object is null");
        }
        validate(line);

        return storage.update(line.getId(), line);
    }

    @Override
    public boolean deleteById(Long id) throws IllegalArgumentException {
        return storage.deleteById(id) != null;
    }

    public void validate(Line line) throws IllegalArgumentException {
        if (line.getName() == null) {
            throw new IllegalArgumentException("Invalid line: name is null");
        }
    }
}
