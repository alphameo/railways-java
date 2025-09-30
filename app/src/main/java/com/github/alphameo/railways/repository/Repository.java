package com.github.alphameo.railways.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    Optional<T> add(final T entity);

    Optional<T> getById(final ID id);

    List<T> getAll();

    Optional<T> update(final T entity);

    boolean deleteById(final ID id);
}
