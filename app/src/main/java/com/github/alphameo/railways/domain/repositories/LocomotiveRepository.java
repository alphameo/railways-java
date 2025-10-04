package com.github.alphameo.railways.domain.repositories;

import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Locomotive;

public interface LocomotiveRepository extends Repository<Locomotive, Long> {

    public Optional<Locomotive> findByNumber(String number);

}
