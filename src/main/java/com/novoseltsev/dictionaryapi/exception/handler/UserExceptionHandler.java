package com.novoseltsev.dictionaryapi.exception.handler;

import com.novoseltsev.dictionaryapi.exception.InvalidOldPasswordException;
import com.novoseltsev.dictionaryapi.exception.LoginIsAlreadyUsedException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils.getErrorResponse;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(LoginIsAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<Object, Object> handleLoginIsAlreadyUsedException(
            LoginIsAlreadyUsedException e
    ) {
        return getErrorResponse(e);
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<Object, Object> handleInvalidOldPasswordException(
            InvalidOldPasswordException e
    ) {
        return getErrorResponse(e);
    }
}
