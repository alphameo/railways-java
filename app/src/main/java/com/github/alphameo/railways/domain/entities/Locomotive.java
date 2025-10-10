package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.domain.valueobjects.LocomotiveModel;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.Data;

@Data
public class Locomotive {

    private Long id;
    private MachineNumber number;
    private LocomotiveModel model;

    public Locomotive(final Long id, final MachineNumber number, final LocomotiveModel model) {
        this.id = id;
        this.setNumber(number);
        this.setModel(model);
    }

    public Locomotive(Locomotive locomotive) {
        new Locomotive(
                locomotive.id,
                locomotive.number,
                locomotive.model);
    }

    public void setNumber(MachineNumber number) {
        if (number == null) {
            throw new ValidationException("Locomotive.number cannot be null");
        }

        this.number = number;
    }

    public void setModel(final LocomotiveModel model) {
        if (model == null) {
            throw new ValidationException("Locomotive.model cannot be null");
        }

        this.model = model;
    }
}
