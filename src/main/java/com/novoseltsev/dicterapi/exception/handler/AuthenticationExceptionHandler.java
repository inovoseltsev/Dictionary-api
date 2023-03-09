package com.novoseltsev.dicterapi.exception.handler;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.novoseltsev.dicterapi.exception.util.ExceptionUtils.getErrorResponse;

@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private Map<Object, Object> handleBadCredentialsException(BadCredentialsException e) {
        return getErrorResponse(e);
    }
}
