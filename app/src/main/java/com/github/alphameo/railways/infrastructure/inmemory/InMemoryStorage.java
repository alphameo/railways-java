package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.alphameo.railways.exceptions.infrastructure.InMemoryException;

public class InMemoryStorage<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    public T create(final ID id, final T entity) {
        if (entity == null) {
            throw new InMemoryException("Entity cannot be null");
        }
        if (id == null) {
            throw new InMemoryException("Entity.id cannot be null");
        }

        if (storage.containsKey(id)) {
            throw new InMemoryException(String.format("Entity with id=%s already exist", id));
        }
        storage.put(id, entity);
        var created = storage.get(id);
        return created;
    }

    public Optional<T> getById(final ID id) {
        if (id == null) {
            throw new InMemoryException("Entity.id cannot be null");
        }

        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public T update(final ID id, final T entity) {
        if (entity == null) {
            throw new InMemoryException("Entity cannot be null");
        }
        if (id == null) {
            throw new InMemoryException("Entity.id cannot be null");
        }

        if (!storage.containsKey(id)) {
            throw new InMemoryException(String.format("Entity with id=%s does not exist", id));
        }
        storage.put(id, entity);
        var updated = storage.get(id);
        return updated;
    }

    public void deleteById(final ID id) {
        if (id == null) {
            throw new InMemoryException("Entity.id cannot be null");
        }

        storage.remove(id);
    }
}
