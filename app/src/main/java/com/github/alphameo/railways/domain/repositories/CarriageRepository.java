package com.github.alphameo.railways.domain.repositories;

import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;

public interface CarriageRepository extends Repository<Carriage, Long> {

    public Optional<Carriage> findByNumber(String number);
}
