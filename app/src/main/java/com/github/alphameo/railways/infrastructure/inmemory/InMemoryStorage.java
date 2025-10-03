package com.github.alphameo.railways.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.github.alphameo.railways.exceptions.InMemoryException;

public class InMemoryStorage<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    public void create(final ID id, final T entity) {
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(entity, "entity cannot be null");

        if (storage.containsKey(id)) {
            throw new InMemoryException(String.format("Entity with id=%s already exist", id));
        }
        storage.put(id, entity);
    }

    public Optional<T> getById(final ID id) {
        Objects.requireNonNull(id, "id cannot be null");

        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void update(final ID id, final T entity) {
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(entity, "entity cannot be null");

        if (!storage.containsKey(id)) {
            throw new InMemoryException(String.format("Entity with id=%s does not exist", id));
        }
        storage.put(id, entity);
    }

    public void deleteById(final ID id) {
        Objects.requireNonNull(id, "id cannot be null");

        storage.remove(id);
    }
}
