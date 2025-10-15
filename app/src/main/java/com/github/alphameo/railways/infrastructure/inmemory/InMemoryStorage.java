package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryConstraintException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryEntityNotExistsException;
import com.github.alphameo.railways.exceptions.infrastructure.inmemory.InMemoryNotNullConstraintException;

public class InMemoryStorage<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    public void create(final ID id, final T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (id == null) {
            throw new InMemoryNotNullConstraintException(String.format("%s.id", entity.getClass().toString()));
        }

        if (storage.containsKey(id)) {
            throw new InMemoryConstraintException(String.format("Entity with id=%s already exist", id));
        }
        storage.put(id, entity);
    }

    public Optional<T> getById(final ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void update(final ID id, final T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (id == null) {
            throw new InMemoryNotNullConstraintException(String.format("%s.id", entity.getClass().toString()));
        }

        if (!storage.containsKey(id)) {
            throw new InMemoryEntityNotExistsException(entity.getClass().toString(), id);
        }
        storage.put(id, entity);
    }

    public void deleteById(final ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        storage.remove(id);
    }
}
