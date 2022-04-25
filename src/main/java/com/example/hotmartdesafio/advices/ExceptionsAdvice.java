package com.example.hotmartdesafio.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionsAdvice {
    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorModel notFoundHandler(NoSuchElementException ex) {
        return new ErrorModel(
                ex.getMessage(),
                "element not found error",
                HttpStatus.NOT_FOUND.toString()
        );
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorModel validationFailed(ConstraintViolationException ex) {
        return new ErrorModel(
                ex.getMessage(),
                "validation error",
                HttpStatus.BAD_REQUEST.toString()
        );
    }
}
