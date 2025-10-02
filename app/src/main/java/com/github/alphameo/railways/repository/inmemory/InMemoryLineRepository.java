package com.github.alphameo.railways.repository.inmemory;

import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.Line;
import com.github.alphameo.railways.repository.LineRepository;

public class InMemoryLineRepository implements LineRepository {

    private final InMemoryStorage<Line, Long> storage = new InMemoryStorage<>();
    private Long idGenerator = 0L;

    @Override
    public Line add(Line line) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException("Invalid line: object is null");
        }
        validate(line);
        if (line.getId() == null) {
            long id = ++idGenerator;
            line.setId(id);
        }

        storage.add(line.getId(), line);
        return line;
    }

    @Override
    public Optional<Line> findById(Long id) throws IllegalArgumentException {
        return storage.getById(id);
    }

    @Override
    public List<Line> findAll() {
        return storage.findAll();
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
