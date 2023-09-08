package com.amigoes.fullstack.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceDuplicateFoundException extends RuntimeException{
    public ResourceDuplicateFoundException(String message) {
        super(message);
    }
}
