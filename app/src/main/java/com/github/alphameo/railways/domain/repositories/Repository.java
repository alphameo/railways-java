package com.github.alphameo.railways.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    T create(final T entity);

    Optional<T> findById(final ID id);

    List<T> findAll();

    T update(final T entity);

    void deleteById(final ID id);
}
