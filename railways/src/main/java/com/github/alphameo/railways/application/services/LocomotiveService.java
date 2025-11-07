package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LocomotiveDto;

public interface LocomotiveService {

     void registerLocomotive(LocomotiveDto locomotive);

     LocomotiveDto findLocomotiveById(String id);

     LocomotiveDto findLocomotiveByNumber(String number);

     List<LocomotiveDto> listAllLocomotives();

     void unregisterLocomotiveById(String id);
}
