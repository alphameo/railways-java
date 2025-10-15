package com.github.alphameo.railways.domain.repositories;

import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;

public interface CarriageRepository extends Repository<Carriage, Long> {

    public Optional<Carriage> findByNumber(MachineNumber number);
}
