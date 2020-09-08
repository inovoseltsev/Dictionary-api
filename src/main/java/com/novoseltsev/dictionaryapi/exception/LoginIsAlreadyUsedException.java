package com.novoseltsev.dictionaryapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.CONFLICT, reason = "Such login is already used")
public class LoginIsAlreadyUsedException extends RuntimeException {
}
