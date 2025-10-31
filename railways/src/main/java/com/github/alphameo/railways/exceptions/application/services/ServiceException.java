package com.github.alphameo.railways.exceptions.application.services;

import com.github.alphameo.railways.exceptions.application.ApplicationException;

public class ServiceException extends ApplicationException {

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
