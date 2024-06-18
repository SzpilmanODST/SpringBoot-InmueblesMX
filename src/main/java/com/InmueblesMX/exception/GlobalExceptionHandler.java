package com.InmueblesMX.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleInvalidArgument(MethodArgumentNotValidException exception) {

        List<String> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        return errors;

    }
}
