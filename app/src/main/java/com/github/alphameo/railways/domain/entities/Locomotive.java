package com.github.alphameo.railways.domain.entities;

import com.github.alphameo.railways.domain.valueobjects.LocomotiveModel;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.domain.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Locomotive {

    private Long id;
    private MachineNumber number;
    private LocomotiveModel model;

    public Locomotive(final Long id, final MachineNumber number, final LocomotiveModel model) {
        this.id = id;
        this.changeNumber(number);
        this.changeModel(model);
    }

    public Locomotive(Locomotive locomotive) {
        new Locomotive(
                locomotive.id,
                locomotive.number,
                locomotive.model);
    }

    public void changeNumber(MachineNumber number) {
        if (number == null) {
            throw new ValidationException("Locomotive.number cannot be null");
        }

        this.number = number;
    }

    public void changeModel(final LocomotiveModel model) {
        if (model == null) {
            throw new ValidationException("Locomotive.model cannot be null");
        }

        this.model = model;
    }
}
