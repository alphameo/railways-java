package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.domain.valueobjects.Id;

public interface CarriageService {

    public void register(CarriageDto carriage);

    public CarriageDto findById(Id id);

    public CarriageDto findByNumber(String number);

    public List<CarriageDto> listAll();

    public void unregister(Id id);
}
