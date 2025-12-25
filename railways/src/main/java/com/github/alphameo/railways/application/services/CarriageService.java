package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.CarriageDto;

public interface CarriageService {

    public void registerCarriage(CarriageDto carriage);

    public void updateCarriage(CarriageDto carriage);

    public CarriageDto findCarriageById(String id);

    public CarriageDto findCarriageByNumber(String number);

    public List<CarriageDto> listAllCarriages();

    public void unregisterCarriage(String id);
}
