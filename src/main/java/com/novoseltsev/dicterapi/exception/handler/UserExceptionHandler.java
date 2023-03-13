package com.novoseltsev.dicterapi.exception.handler;

import com.novoseltsev.dicterapi.exception.InvalidOldPasswordException;
import com.novoseltsev.dicterapi.exception.LoginIsAlreadyUsedException;
import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({InvalidOldPasswordException.class, LoginIsAlreadyUsedException.class})
    public ErrorResponse handleInvalidOldPasswordException(Exception e) {
        return new ErrorResponse(HttpStatus.CONFLICT, e);
    }
}
