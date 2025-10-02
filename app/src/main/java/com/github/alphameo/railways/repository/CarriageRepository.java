package com.github.alphameo.railways.repository;

import java.util.Optional;

import com.github.alphameo.railways.domain.Carriage;

public interface CarriageRepository extends Repository<Carriage, Long> {

    public Optional<Carriage> findByNumber(String number);
}
