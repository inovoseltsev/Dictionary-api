package com.novoseltsev.dicterapi.exception.handler;

import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    private ErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, e);
    }
}
