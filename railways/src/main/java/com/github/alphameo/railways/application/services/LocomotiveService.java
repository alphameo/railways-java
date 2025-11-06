package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LocomotiveDto;

public interface LocomotiveService {

     void register(LocomotiveDto locomotive);

     LocomotiveDto findById(String id);

     LocomotiveDto findByNumber(String number);

     List<LocomotiveDto> listAll();

     void unregister(String id);
}
