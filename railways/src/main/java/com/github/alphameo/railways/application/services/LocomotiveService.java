package com.github.alphameo.railways.application.services;

import java.util.List;

import com.github.alphameo.railways.application.dto.LocomotiveDto;

public interface LocomotiveService {

     void register(LocomotiveDto locomotive) ;

     LocomotiveDto findById(Long id) ;

     LocomotiveDto findByNumber(String number) ;

     List<LocomotiveDto> listAll() ;

     void unregister(Long id) ;
}
