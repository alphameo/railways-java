package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.CarriageDto;

public interface CarriageService {

    public void register(CarriageDto carriage);

    public CarriageDto findById(String id);

    public CarriageDto findByNumber(String number);

    public List<CarriageDto> listAll();

    public void unregister(String id);
}
