package com.github.alphameo.railways.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    T add(final T entity) throws IllegalArgumentException;

    Optional<T> findById(final ID id) throws IllegalArgumentException;

    List<T> findAll();

    boolean update(final T entity) throws IllegalArgumentException;

    boolean deleteById(final ID id) throws IllegalArgumentException;
}
