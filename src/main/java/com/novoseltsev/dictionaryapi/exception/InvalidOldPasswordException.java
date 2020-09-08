package com.novoseltsev.dictionaryapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.CONFLICT, reason = "Old password is incorrect")
public class InvalidOldPasswordException extends RuntimeException {
}
