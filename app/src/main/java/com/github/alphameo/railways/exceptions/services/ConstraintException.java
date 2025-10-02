package com.github.alphameo.railways.exceptions.services;

import com.github.alphameo.railways.exceptions.services.ServiceException;

public class ConstraintException extends ServiceException {

    public ConstraintException(String msg) {
        super(msg);
    }
}
