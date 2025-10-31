package com.github.alphameo.railways.domain.repositories;

import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;

public interface TrainRepository extends Repository<Train, Id> {

    public Optional<Train> findByNumber(MachineNumber number);
}
