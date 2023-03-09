package com.novoseltsev.dicterapi.exception.handler;

import com.novoseltsev.dicterapi.exception.InvalidOldPasswordException;
import com.novoseltsev.dicterapi.exception.LoginIsAlreadyUsedException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.novoseltsev.dicterapi.exception.util.ExceptionUtils.getErrorResponse;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(LoginIsAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<Object, Object> handleLoginIsAlreadyUsedException(LoginIsAlreadyUsedException e) {
        return getErrorResponse(e);
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<Object, Object> handleInvalidOldPasswordException(InvalidOldPasswordException e) {
        return getErrorResponse(e);
    }
}
