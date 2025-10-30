package com.github.alphameo.railways.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    void create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T entity);

    void deleteById(ID id);
}
