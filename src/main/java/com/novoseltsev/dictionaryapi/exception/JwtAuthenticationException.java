package com.novoseltsev.dictionaryapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,
        reason = "Jwt token is expired or invalid")
public class JwtAuthenticationException extends RuntimeException {
}
