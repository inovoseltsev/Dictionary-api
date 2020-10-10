package com.novoseltsev.dictionaryapi.exception.handler;

import com.novoseltsev.dictionaryapi.exception.ObjectNotFoundException;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import static com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils.getErrorResponse;
import static com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils.handleValidationErrors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        return new ResponseEntity<>(handleValidationErrors(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<Object, Object> handleObjectNotFoundException(ObjectNotFoundException e) {
        return getErrorResponse(e);
    }
}
