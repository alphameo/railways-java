package com.github.alphameo.railways.repository.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStorage<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    public void add(ID id, T entity) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: id cannot be null");
        }
        if (storage.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Entity with id=%s already exists", id));
        }
        storage.put(id, entity);
    }

    public Optional<T> getById(ID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean update(ID id, T entity) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (!storage.containsKey(id)) {
            return false;
        }
        storage.put(id, entity);
        return true;
    }

    public T deleteById(ID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return storage.remove(id);
    }
}
