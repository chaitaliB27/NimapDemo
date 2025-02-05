package com.nimap.curdOperation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class IdNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IdNotFoundException(String message)
    {
        super(message);
    }
}
