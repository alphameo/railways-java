package com.github.alphameo.railways.exceptions.services;

import com.github.alphameo.railways.exceptions.services.ServiceException;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
