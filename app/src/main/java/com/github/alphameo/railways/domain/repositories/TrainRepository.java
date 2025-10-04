package com.github.alphameo.railways.domain.repositories;

import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;

public interface TrainRepository extends Repository<Train, Long> {

    public Optional<Train> findByNumber(String number);
}
