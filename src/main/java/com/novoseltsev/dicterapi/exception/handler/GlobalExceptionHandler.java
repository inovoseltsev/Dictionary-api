package com.novoseltsev.dicterapi.exception.handler;

import com.novoseltsev.dicterapi.exception.ObjectNotFoundException;
import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var validationErrors = ex.getBindingResult().getAllErrors().stream()
            .map(error -> {
                String errorMessage = "validation: " + error.getDefaultMessage();
                return new ErrorResponse(status.getReasonPhrase(), errorMessage, status.value());
            })
            .collect(Collectors.toList());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ApiResponse(responseCode = "400")
    @ExceptionHandler({IOException.class, ObjectNotFoundException.class})
    public ErrorResponse handleBadRequestExceptions(Exception e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e);
    }
}
