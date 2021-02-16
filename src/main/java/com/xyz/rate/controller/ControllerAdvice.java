package com.xyz.rate.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.xyz.rate.dto.ErrorMessage;
import com.xyz.rate.exception.client.BadRequestException;
import com.xyz.rate.exception.client.RateNotFoundException;
import com.xyz.rate.exception.server.InternalServerException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    public static final String CLIENT_ERROR = "CLIENT_ERROR";

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorMessage badRequestErrorHandler(final BadRequestException exception) {
        return exception.getErrorMessage();
    }

    @ExceptionHandler({ InternalServerException.class })
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ErrorMessage internalServerErrorHandler(final InternalServerException exception) {
        return exception.getErrorMessage();
    }

    @ExceptionHandler({ RateNotFoundException.class })
    @ResponseStatus(NOT_FOUND)
    ErrorMessage rateNotFoundExceptionHandler(final RateNotFoundException exception) {
        return exception.getErrorMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorMessage pathParamValidatorConstraintErrorHandler(final ConstraintViolationException exception) {
        return ErrorMessage.builder()
            .errorCode(CLIENT_ERROR)
            .errorMessage(exception
                .getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s",
                    StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                        .map(Path.Node::getName)
                        .reduce((s, s2) -> s2)
                        .orElse("unknown"),
                    violation.getMessage()))
                .collect(Collectors.toList())
            )
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorMessage bodyValidatorConstraintErrorHandler(final MethodArgumentNotValidException exception) {
        return ErrorMessage.builder()
            .errorCode(CLIENT_ERROR)
            .errorMessage(exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s",
                    fieldError.getField(),
                    fieldError.getDefaultMessage()))
                .collect(Collectors.toList()))
            .build();
    }
}
