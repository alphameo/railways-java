package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.domain.valueobjects.Id;

public interface LocomotiveService {

     void register(LocomotiveDto locomotive) ;

     LocomotiveDto findById(Id id) ;

     LocomotiveDto findByNumber(String number) ;

     List<LocomotiveDto> listAll() ;

     void unregister(Id id) ;
}
